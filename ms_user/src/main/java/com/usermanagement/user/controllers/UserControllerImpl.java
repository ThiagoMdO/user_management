package com.usermanagement.user.controllers;

import com.usermanagement.user.interfaces.UserController;
import com.usermanagement.user.model.dto.in.UserRequestCreateDTO;
import com.usermanagement.user.model.dto.out.UserResponseCreatedDTO;
import com.usermanagement.user.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @PostMapping()
    @Override
    public ResponseEntity<UserResponseCreatedDTO> createUser(@Valid @RequestBody UserRequestCreateDTO requestDTO) {
        UserResponseCreatedDTO createdDTO = userService.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

}
