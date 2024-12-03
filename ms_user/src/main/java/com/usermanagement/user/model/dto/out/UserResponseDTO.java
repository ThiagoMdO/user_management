package com.usermanagement.user.model.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usermanagement.user.model.entities.Role;
import com.usermanagement.user.model.entities.User;

import java.time.LocalDate;
import java.util.List;

public record UserResponseDTO(
    String firstName,

    String lastName,

    String cpf,

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate date,

    String email,

    List<Role> roles
) {
    public static UserResponseDTO createdDTO(User user){
        return new UserResponseDTO(user.getFirstName(),
                                        user.getLastName(),
                                        user.getCpf(),
                                        user.getDate(),
                                        user.getEmail(),
                                        user.getRoles());
    }
}
