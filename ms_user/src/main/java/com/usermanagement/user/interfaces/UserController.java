package com.usermanagement.user.interfaces;

import com.usermanagement.user.model.dto.in.UserRequestCreateDTO;
import com.usermanagement.user.model.dto.out.UserResponseCreatedDTO;
import org.springframework.http.ResponseEntity;

public interface UserController {

    ResponseEntity<UserResponseCreatedDTO> createUser(UserRequestCreateDTO requestDTO);

}
