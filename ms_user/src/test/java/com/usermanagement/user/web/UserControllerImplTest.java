package com.usermanagement.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagement.user.controllers.UserControllerImpl;
import com.usermanagement.user.services.UserService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.usermanagement.user.common.UserConstants.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("create: validFields > ReturnsUserResponseCreatedDTO : Status_201")
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
}
