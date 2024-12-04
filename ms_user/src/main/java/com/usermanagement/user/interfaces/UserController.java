package com.usermanagement.user.interfaces;

import com.usermanagement.user.model.dto.in.UserRequestCreateDTO;
import com.usermanagement.user.model.dto.in.UserRequestUpdateDTO;
import com.usermanagement.user.model.dto.out.UserResponseDTO;
import com.usermanagement.user.model.dto.out.UserUpdatedResponseDTO;
import org.springframework.http.ResponseEntity;

public interface UserController {

    ResponseEntity<UserResponseDTO> createUser(UserRequestCreateDTO requestDTO);

    ResponseEntity<UserResponseDTO> getUserById(String id);

    ResponseEntity<UserUpdatedResponseDTO> updateUser(String sectionIdUser, UserRequestUpdateDTO requestUpdateDTO);

}
