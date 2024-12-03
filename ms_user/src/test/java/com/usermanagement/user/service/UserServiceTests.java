package com.usermanagement.user.service;

import com.usermanagement.user.enums.UserRolesEnum;
import com.usermanagement.user.exceptions.customException.CPFAlreadyInUseException;
import com.usermanagement.user.exceptions.customException.EmailAlreadyInUseException;
import com.usermanagement.user.exceptions.customException.UserNotFoundException;
import com.usermanagement.user.model.dto.out.UserResponseDTO;
import com.usermanagement.user.repositories.RoleRepository;
import com.usermanagement.user.repositories.UserRepository;
import com.usermanagement.user.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.usermanagement.user.common.RolesConstants.ROLE_ADM;
import static com.usermanagement.user.common.RolesConstants.ROLE_USER;
import static com.usermanagement.user.common.UserConstants.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
class UserServiceTests {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Test
    @DisplayName("createUser: UserRequestValidFields_WithoutRole > ReturnsUserResponseDTO")
    void createUser_withUserRequestValidFields_ReturnsUserResponseCreatedDTO() {
        when(userRepository.findByCpf(USER_COMMON_01_REQUEST_01.cpf())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(USER_COMMON_01_REQUEST_01.email())).thenReturn(Optional.empty());
        when(roleRepository.findByTypeRole(UserRolesEnum.COMMON_USER)).thenReturn(Optional.of(ROLE_USER));
        when(userRepository.save(USER_COMMON_01_REQUEST_01_CREATED_WITHOUT_ID)).thenReturn(USER_COMMON_01_REQUEST_01_CREATED);

        UserResponseDTO result = userService.create(USER_COMMON_01_REQUEST_01);

        assertNotNull(result);
        assertEquals(USER_COMMON_01_RESPONSE_01_CREATED, result);
        verify(userRepository,times(1)).findByCpf(any());
        verify(userRepository, times(1)).findByEmail(any());
        verify(roleRepository, times(2)).findByTypeRole(any());
        verify(userRepository, times(1)).save(any());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    @DisplayName("createUser: UserRequestValidFields_WithRoleADM_AND_COMMON > ReturnsUserResponseDTO")
    void createUser_UserRequestValidFields_WithRoleADM_WithRoleADM_AND_COMMON_ReturnsUserResponseCreateDTO() {
        when(userRepository.findByCpf(USER_COMMON_02_REQUEST_02.cpf())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(USER_COMMON_02_REQUEST_02.email())).thenReturn(Optional.empty());
        when(roleRepository.findByTypeRole(UserRolesEnum.ADM_USER)).thenReturn(Optional.of(ROLE_ADM));
        when(roleRepository.findByTypeRole(UserRolesEnum.COMMON_USER)).thenReturn(Optional.of(ROLE_USER));
        when(userRepository.save(USER_COMMON_02_REQUEST_02_CREATED_WITHOUT_ID)).thenReturn(USER_COMMON_02_REQUEST_02_CREATED);

        UserResponseDTO result = userService.create(USER_COMMON_02_REQUEST_02);

        assertNotNull(result);
        assertEquals(USER_COMMON_02_RESPONSE_02_CREATED, result);
        verify(userRepository,times(1)).findByCpf(any());
        verify(userRepository, times(1)).findByEmail(any());
        verify(roleRepository, times(8)).findByTypeRole(any());
        verify(userRepository, times(1)).save(any());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    @DisplayName("createUser: UserRequestWithCPFAlreadyInUse > ThrowCPFAlreadyUserException")
    void createUser_UserRequestWithCPFAlreadyInUse_ThrowCPFAlreadyUserException() {
        when(userRepository.findByCpf(USER_COMMON_03_REQUEST_03_CPF_ALREADY_IN_USE.cpf())).thenReturn(Optional.of(USER_COMMON_IN_DB_01));

        assertThrows(CPFAlreadyInUseException.class,
        () -> userService.create(USER_COMMON_03_REQUEST_03_CPF_ALREADY_IN_USE));

        verify(userRepository,times(1)).findByCpf(any());
        verify(userRepository, never()).findByEmail(any());
        verify(roleRepository, never()).findByTypeRole(any());
        verify(userRepository, never()).save(any());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    @DisplayName("createUser: UserRequestWithEmailAlreadyInUse > ThrowEmailAlreadyUserException")
    void createUser_UserRequestWithEmailAlreadyInUse_ThrowEmailAlreadyUserException() {
        when(userRepository.findByCpf(USER_COMMON_04_REQUEST_04_EMAIL_ALREADY_IN_USE.cpf())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(USER_COMMON_04_REQUEST_04_EMAIL_ALREADY_IN_USE.email())).thenReturn(Optional.of(USER_COMMON_IN_DB_01));

        assertThrows(EmailAlreadyInUseException.class,
        () -> userService.create(USER_COMMON_04_REQUEST_04_EMAIL_ALREADY_IN_USE));

        verify(userRepository,times(1)).findByCpf(any());
        verify(userRepository, times(1)).findByEmail(any());
        verify(roleRepository, never()).findByTypeRole(any());
        verify(userRepository, never()).save(any());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    @DisplayName("getUserById: UserIDValid > ReturnsUserResponseDTO")
    void getUserById_UserIDValid_ReturnsUserResponseDTO() {
        when(userRepository.findById(USER_COMMON_01_REQUEST_01_CREATED.getId())).thenReturn(Optional.of(USER_COMMON_01_REQUEST_01_CREATED));

        UserResponseDTO result = userService.getByID(USER_COMMON_01_REQUEST_01_CREATED.getId().toString());

        assertNotNull(result);
        assertEquals(USER_COMMON_01_RESPONSE_01_CREATED, result);
        verify(userRepository, times(1)).findById(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("getUserById: UserIDInvalid > ThrowsUserNotFoundException")
    void getUserById_UserIDInvalid_ThrowsUserNotFoundException() {
        String invalidUserID = "c55eb1b4-c9ac-4a46-8d7a-2f472d9d0ea6";

        when(userRepository.findById(UUID.fromString(invalidUserID))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getByID(invalidUserID));
        verify(userRepository, times(1)).findById(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("getUserById: UserIDOtherFormat > ThrowsIllegalArgumentException")
    void getUserById_UserIDOtherFormat_ThrowsIllegalArgumentException() {
        String incorrectFormatID = "123456";

        assertThrows(IllegalArgumentException.class, () -> userService.getByID(incorrectFormatID));
        verifyNoMoreInteractions(userRepository);
    }


}
