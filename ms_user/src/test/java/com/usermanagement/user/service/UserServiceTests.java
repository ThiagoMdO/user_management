package com.usermanagement.user.service;

import com.usermanagement.user.enums.UserRolesEnum;
import com.usermanagement.user.exceptions.customException.CPFAlreadyInUseException;
import com.usermanagement.user.exceptions.customException.EmailAlreadyInUseException;
import com.usermanagement.user.exceptions.customException.UserCannotBeChangedException;
import com.usermanagement.user.exceptions.customException.UserNotFoundException;
import com.usermanagement.user.model.dto.out.UserResponseDTO;
import com.usermanagement.user.model.dto.out.UserUpdatedResponseDTO;
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
    @DisplayName("create: UserRequestValidFields_WithoutRole > Returns_UserResponseDTO")
    void create_withUserRequestValidFields_Returns_UserResponseDTO() {
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
    @DisplayName("create: UserRequestValidFields_WithRoleADM_AND_COMMON > Returns_UserResponseDTO")
    void create_UserRequestValidFields_WithRoleADM_WithRoleADM_AND_COMMON_Returns_UserResponseDTO() {
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
    @DisplayName("create: UserRequestWithCPFAlreadyInUse > Throws_CPFAlreadyUserException")
    void create_UserRequestWithCPFAlreadyInUse_Throws_CPFAlreadyUserException() {
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
    @DisplayName("create: UserRequestWithEmailAlreadyInUse > Throws_EmailAlreadyUserException")
    void create_UserRequestWithEmailAlreadyInUse_Throws_EmailAlreadyUserException() {
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
    @DisplayName("getByID: UserIDValid > Returns_UserResponseDTO")
    void getByID_UserIDValid_Returns_UserResponseDTO() {
        when(userRepository.findById(USER_COMMON_01_REQUEST_01_CREATED.getId())).thenReturn(Optional.of(USER_COMMON_01_REQUEST_01_CREATED));

        UserResponseDTO result = userService.getByID(USER_COMMON_01_REQUEST_01_CREATED.getId().toString());

        assertNotNull(result);
        assertEquals(USER_COMMON_01_RESPONSE_01_CREATED, result);
        verify(userRepository, times(1)).findById(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("getByID: UserIDInvalid > Throws_UserNotFoundException")
    void getByID_UserIDInvalid_ThrowsUserNotFoundException() {
        String invalidUserID = "c55eb1b4-c9ac-4a46-8d7a-2f472d9d0ea6";

        when(userRepository.findById(UUID.fromString(invalidUserID))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getByID(invalidUserID));
        verify(userRepository, times(1)).findById(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("getByID: UserIDOtherFormat > Throws_IllegalArgumentException")
    void getByID_UserIDOtherFormat_ThrowsIllegalArgumentException() {
        String incorrectFormatID = "123456";

        assertThrows(IllegalArgumentException.class, () -> userService.getByID(incorrectFormatID));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("update: UserRequestValidFields > Returns_UserUpdatedResponseDTO")
    void update_UserRequestValidFields_Returns_UserUpdatedResponseDTO() {
        when(userRepository.findById(USER_COMMON_IN_DB_01.getId())).thenReturn(Optional.of(USER_COMMON_IN_DB_01));
        when(userRepository.save(USER_COMMON_UPLOADED_IN_DB)).thenReturn(USER_COMMON_UPLOADED_IN_DB);

        UserUpdatedResponseDTO result = userService.update(USER_COMMON_IN_DB_01.getId().toString(), USER_COMMON_REQUEST_TO_UPDATE);

        assertNotNull(result);
        assertEquals(USER_COMMON_RESPONSE_UPDATED, result);
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).save(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("update: UserRequestWithSomeFieldsVoid > Returns_UserUpdatedResponseDTO")
    void update_UserRequestWithSomeFieldsVoid_Returns_UserUpdatedResponseDTO() {
        when(userRepository.findById(USER_COMMON_IN_DB_01.getId())).thenReturn(Optional.of(USER_COMMON_IN_DB_01));
        when(userRepository.save(USER_COMMON_UPLOADED_IN_DB_WITH_SOME_FIELDS_VOID))
        .thenReturn(USER_COMMON_UPLOADED_IN_DB_WITH_SOME_FIELDS_VOID);

        UserUpdatedResponseDTO result = userService.update(USER_COMMON_IN_DB_01.getId().toString()
        , USER_COMMON_REQUEST_WITH_SOME_FIELDS_VOID_TO_UPDATE);

        assertNotNull(result);
        assertEquals(USER_COMMON_RESPONSE_UPDATED_WITH_SOME_FIELDS_VOID, result);
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).save(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("update: DisabledUserInDB > Throws_UserCannotBeChangedException")
    void update_DisabledUserInDB_Throws_UserCannotBeChangedException() {
        when(userRepository.findById(USER_DISABLE_COMMON_IN_DB_01.getId())).thenReturn(Optional.of(USER_DISABLE_COMMON_IN_DB_01));

        assertThrows(UserCannotBeChangedException.class,
            () -> userService.update(USER_DISABLE_COMMON_IN_DB_01.getId().toString(), USER_COMMON_REQUEST_TO_UPDATE));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("update: UserIDInvalid > Throws_UserNotFoundException")
    void update_UserIDInvalid_ThrowsUserNotFoundException() {
        String invalidUserID = "c55eb1b4-c9ac-4a46-8d7a-2f472d9d0ea6";

        when(userRepository.findById(UUID.fromString(invalidUserID))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.update(invalidUserID, USER_COMMON_REQUEST_TO_UPDATE));
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, never()).save(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("update: UserIDOtherFormat > Throws_IllegalArgumentException")
    void update_UserIDOtherFormat_ThrowsIllegalArgumentException() {
        String incorrectFormatID = "123456";

        assertThrows(IllegalArgumentException.class, () -> userService.update(incorrectFormatID, USER_COMMON_REQUEST_TO_UPDATE));
        verify(userRepository, never()).findById(any());
        verify(userRepository, never()).save(any());
        verifyNoMoreInteractions(userRepository);
    }

}
