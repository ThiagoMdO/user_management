package com.usermanagement.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagement.user.controllers.UserControllerImpl;
import com.usermanagement.user.exceptions.customException.UserCannotBeChangedException;
import com.usermanagement.user.exceptions.customException.UserNotFoundException;
import com.usermanagement.user.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;
import java.util.UUID;

import static com.usermanagement.user.common.UserConstants.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerImplTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("create: validFields > ReturnsUserResponseDTO : Status_201")
    void create_ValidFields_ReturnsUserResponseCreatedDTO_Status_201() throws Exception {
        when(userService.create(USER_COMMON_01_REQUEST_01)).thenReturn(USER_COMMON_01_RESPONSE_01_CREATED);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_COMMON_01_REQUEST_01)))
                        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("create: InvalidFields > ThrowsMethodArgumentNotValidException : Status_400")
    void create_InvalidFields_ThrowsMethodArgumentNotValidException_Status_400() throws Exception {

        mockMvc.perform(post("/v1/users")
            .content(objectMapper.writeValueAsString(USER_COMMON_01_INVALID_REQUEST_01))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @DisplayName("getUserById: ValidUserID > ReturnsUserResponseDTO : Status_200")
    void getUserById_ValidUserID_ReturnsUserResponseDTO_Status_200() throws Exception{

        when(userService.getByID(USER_COMMON_01_REQUEST_01_CREATED.getId().toString()))
        .thenReturn(USER_COMMON_01_RESPONSE_01_CREATED);

        mockMvc.perform(get("/v1/users/" + USER_COMMON_01_REQUEST_01_CREATED.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(USER_COMMON_01_RESPONSE_01_CREATED)));
    }
    //TODO Tests 404 and 400, from getUserById

    @Test
    @DisplayName("updateUser: ValidFields > ReturnsUserUpdatedResponseDTO : Status_200")
    void updateUser_ValidFields_ReturnsUserUpdatedResponseDTO_Status_200() throws Exception{
        when(userService.update(USER_COMMON_IN_DB_01.getId().toString(), USER_COMMON_REQUEST_TO_UPDATE))
                .thenReturn(USER_COMMON_RESPONSE_UPDATED);

        mockMvc.perform(put("/v1/users/" + USER_COMMON_IN_DB_01.getId())
                .content(objectMapper.writeValueAsString(USER_COMMON_REQUEST_TO_UPDATE))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("updateUser: UserRequestWithSomeFieldsVoid > Returns_UserUpdatedResponseDTO : Status_200")
    void updateUser_UserRequestWithSomeFieldsVoid_Returns_UserUpdatedResponseDTO_Status_200() throws Exception{
        when(userService.update(USER_COMMON_IN_DB_01.getId().toString(),
                USER_COMMON_REQUEST_WITH_SOME_FIELDS_VOID_TO_UPDATE))
                .thenReturn(USER_COMMON_RESPONSE_UPDATED_WITH_SOME_FIELDS_VOID);

        mockMvc.perform(put("/v1/users/" + USER_COMMON_IN_DB_01.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(USER_COMMON_REQUEST_WITH_SOME_FIELDS_VOID_TO_UPDATE)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("updateUser: DisabledUserInDB > Throws_UserCannotBeChangedException : Status_400")
    void updateUser_DisabledUserInDB_Throws_UserCannotBeChangedException() throws Exception{
        when(userService.update(USER_DISABLE_COMMON_IN_DB_01.getId().toString(), USER_COMMON_REQUEST_TO_UPDATE))
                .thenThrow(UserCannotBeChangedException.class);

        mockMvc.perform(put("/v1/users/" + USER_DISABLE_COMMON_IN_DB_01.getId())
                    .content(objectMapper.writeValueAsString(USER_COMMON_REQUEST_TO_UPDATE))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("updateUser: UserIDInvalid > Throws_UserNotFoundException : Status_404")
    void updateUser_UserIDInvalid_ThrowsUserNotFoundException() throws Exception{
        String invalidUserID = "c55eb1b4-c9ac-4a46-8d7a-2f472d9d0ea6";

        when(userService.update(invalidUserID, USER_COMMON_REQUEST_TO_UPDATE))
                .thenThrow(UserNotFoundException.class);

        mockMvc.perform(put("/v1/users/" + invalidUserID)
                        .content(objectMapper.writeValueAsString(USER_COMMON_REQUEST_TO_UPDATE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("updateUser: UserIDOtherFormat > Throws_IllegalArgumentException : Status_400")
    void update_UserIDOtherFormat_ThrowsIllegalArgumentException_Status_400() throws Exception{
        String incorrectFormatID = "123456";
        when(userService.update(incorrectFormatID, USER_COMMON_REQUEST_TO_UPDATE))
                .thenThrow(IllegalArgumentException.class);

        mockMvc.perform(put("/v1/users/" + incorrectFormatID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(USER_COMMON_REQUEST_TO_UPDATE)))
                        .andExpect(status().isBadRequest());
    }
}
