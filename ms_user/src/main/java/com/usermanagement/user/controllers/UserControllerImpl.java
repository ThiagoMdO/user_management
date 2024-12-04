package com.usermanagement.user.controllers;

import com.usermanagement.user.interfaces.UserController;
import com.usermanagement.user.model.dto.in.UserRequestCreateDTO;
import com.usermanagement.user.model.dto.in.UserRequestUpdateDTO;
import com.usermanagement.user.model.dto.out.UserResponseDTO;
import com.usermanagement.user.model.dto.out.UserUpdatedResponseDTO;
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

    @PostMapping
    @Override
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestCreateDTO requestDTO) {
        UserResponseDTO createdDTO = userService.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @GetMapping({"/{id}"})
    @Override
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        UserResponseDTO userResponseDTO = userService.getByID(id);

        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping({"/{id}"})
    @Override
    public ResponseEntity<UserUpdatedResponseDTO> updateUser(@PathVariable("id") String sectionIdUser, @RequestBody UserRequestUpdateDTO requestUpdateDTO) {
        UserUpdatedResponseDTO userResponseDTO = userService.update(sectionIdUser, requestUpdateDTO);

        return ResponseEntity.ok(userResponseDTO);
    }

}
