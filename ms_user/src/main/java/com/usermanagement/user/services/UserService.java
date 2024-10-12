package com.usermanagement.user.services;

import com.usermanagement.user.enums.UserRolesEnum;
import com.usermanagement.user.exceptions.customException.CPFAlreadyInUseException;
import com.usermanagement.user.exceptions.customException.EmailAlreadyInUseException;
import com.usermanagement.user.model.dto.in.UserRequestCreateDTO;
import com.usermanagement.user.model.dto.out.UserResponseCreatedDTO;
import com.usermanagement.user.model.entities.Role;
import com.usermanagement.user.model.entities.User;
import com.usermanagement.user.repositories.RoleRepository;
import com.usermanagement.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserResponseCreatedDTO create(UserRequestCreateDTO requestDTO) {
        checkCPFAvailable(requestDTO.cpf());
        checkEmailAvailable(requestDTO.email());

        UserRequestCreateDTO userRequestCreateDTO = checkRole(requestDTO).isPresent() ?
        checkRole(requestDTO).get() :
        null;

        var userCreated = userRepository.save(createUser(userRequestCreateDTO));

        return UserResponseCreatedDTO.createdDTO(userCreated);
    }

    private Optional<UserRequestCreateDTO> checkRole(UserRequestCreateDTO requestDTO) {

        var requestDTOTOCreate = (requestDTO.roles() == null || requestDTO.roles().isEmpty()) ?
        checkWithoutRole(requestDTO) :
        checkWithRole(requestDTO);

        return (requestDTOTOCreate.isPresent()) ? requestDTOTOCreate : Optional.empty();
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
