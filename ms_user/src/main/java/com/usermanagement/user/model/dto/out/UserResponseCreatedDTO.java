package com.usermanagement.user.model.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usermanagement.user.model.entities.Role;
import com.usermanagement.user.model.entities.User;

import java.time.LocalDate;
import java.util.List;

public record UserResponseCreatedDTO(
    String firstName,

    String lastName,

    String cpf,

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate date,

    String email,

    List<Role> roles
) {
    public static UserResponseCreatedDTO createdDTO(User user){
        return new UserResponseCreatedDTO(user.getFirstName(),
                                        user.getLastName(),
                                        user.getCpf(),
                                        user.getDate(),
                                        user.getEmail(),
                                        user.getRoles());
    }
}
