package com.usermanagement.user.model.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usermanagement.user.model.entities.User;

import java.time.LocalDate;

public record UserUpdatedResponseDTO(
    String firstName,

    String lastName,

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate date,

    String email,

    Boolean active
) {
    public static UserUpdatedResponseDTO createDTO(User user) {
        return new UserUpdatedResponseDTO(user.getFirstName(),
        user.getLastName(),
        user.getDate(),
        user.getEmail(),
        user.isActive());
    }
}
