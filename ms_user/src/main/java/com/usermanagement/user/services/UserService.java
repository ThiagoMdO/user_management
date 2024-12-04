package com.usermanagement.user.services;

import com.usermanagement.user.enums.UserRolesEnum;
import com.usermanagement.user.exceptions.customException.CPFAlreadyInUseException;
import com.usermanagement.user.exceptions.customException.EmailAlreadyInUseException;
import com.usermanagement.user.exceptions.customException.UserNotFoundException;
import com.usermanagement.user.model.dto.in.UserRequestCreateDTO;
import com.usermanagement.user.model.dto.in.UserRequestUpdateDTO;
import com.usermanagement.user.model.dto.out.UserResponseDTO;
import com.usermanagement.user.model.dto.out.UserUpdatedResponseDTO;
import com.usermanagement.user.model.entities.Role;
import com.usermanagement.user.model.entities.User;
import com.usermanagement.user.repositories.RoleRepository;
import com.usermanagement.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserResponseDTO create(UserRequestCreateDTO requestDTO) {
        logger.info("Iniciando cpf{}", requestDTO.cpf());

        checkCPFAvailable(requestDTO.cpf());
        checkEmailAvailable(requestDTO.email());

        UserRequestCreateDTO userRequestCreateDTO = checkRole(requestDTO).isPresent() ?
        checkRole(requestDTO).get() :
        null;

        var userCreated = userRepository.save(createUser(userRequestCreateDTO));

        logger.info("ID: {}", userCreated.getId());

        return UserResponseDTO.createdDTO(userCreated);
    }

    public UserResponseDTO getByID(String id) {
//        List<User> userInBD = userRepository.findAll();
//
//        for (User user : userInBD) {
//            logger.info("name {}, id: {}", user.getFirstName(), user.getId());
//        }
        return getUserInBD(id);
    }

    public UserUpdatedResponseDTO update(String sectionUserID, UserRequestUpdateDTO requestUpdateDTO) {

        var userToUpload = updateUserBuilder(sectionUserID, requestUpdateDTO);

        return UserUpdatedResponseDTO.createDTO(userToUpload);
    }

    private User updateUserBuilder(String sectionUserID, UserRequestUpdateDTO requestUpdateDTO) {
        User userInBD = getOptionalUserInBD(sectionUserID);

        User userToUpload = User.update(userInBD, Optional.of(requestUpdateDTO));

        return userRepository.save(userToUpload);
    }

    private UserResponseDTO getUserInBD(String id) {
        var checkUserInDB = getOptionalUserInBD(id);
        return UserResponseDTO.createdDTO(checkUserInDB);
    }

    private User getOptionalUserInBD(String id) {
        return userRepository.findById(UUID.fromString(id)).orElseThrow(UserNotFoundException::new);
    }

    private Optional<UserRequestCreateDTO> checkRole(UserRequestCreateDTO requestDTO) {

        return (requestDTO.roles() == null || requestDTO.roles().isEmpty()) ?
        checkWithoutRole(requestDTO) :
        checkWithRole(requestDTO);
    }

    private Optional<UserRequestCreateDTO> checkWithRole(UserRequestCreateDTO requestDTO) {
        List<Role> roleList = new ArrayList<>();

        for (Role role : requestDTO.roles()) {
            if (roleRepository.findByTypeRole(role.getTypeRole()).isPresent())
                roleList.add(roleRepository.findByTypeRole(role.getTypeRole()).get());
        }

        requestDTO.roles().clear();
        requestDTO.roles().addAll(roleList);

        return Optional.of(requestDTO);
    }


    private User createUser(UserRequestCreateDTO requestCreateDTO) {
        return User.create(requestCreateDTO);
    }

    private Optional<UserRequestCreateDTO> checkWithoutRole(UserRequestCreateDTO requestCreateDTO) {
        requestCreateDTO = getUserRequestCreateDTOWithoutRole(requestCreateDTO);

        return Optional.of(requestCreateDTO);
    }

    private UserRequestCreateDTO getUserRequestCreateDTOWithoutRole(UserRequestCreateDTO requestCreateDTO) {
        List<Role> roles = new ArrayList<>();

        Optional<Role> byTypeRole = roleRepository.findByTypeRole(UserRolesEnum.COMMON_USER);

        byTypeRole.ifPresent(roles::add);

        requestCreateDTO = new UserRequestCreateDTO(
        requestCreateDTO.firstName(),
        requestCreateDTO.lastName(),
        requestCreateDTO.cpf(),
        requestCreateDTO.date(),
        requestCreateDTO.email(),
        requestCreateDTO.password(),
        requestCreateDTO.active(),
        roles
        );
        return requestCreateDTO;
    }

    private void checkCPFAvailable(String cpf) {
        if (userRepository.findByCpf(cpf).isPresent()) throw new CPFAlreadyInUseException();
    }

    private void checkEmailAvailable(String email) {
        if (userRepository.findByEmail(email).isPresent()) throw new EmailAlreadyInUseException();
    }

}
