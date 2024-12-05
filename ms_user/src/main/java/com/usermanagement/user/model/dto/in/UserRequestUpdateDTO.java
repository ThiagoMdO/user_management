package com.usermanagement.user.model.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Optional;

public record UserRequestUpdateDTO(
    @Size(min = 2, message = "First name needs at least 2 characters")
    Optional<String> firstName,

    @Size(min = 2, message = "Last name needs at least 2 characters")
    Optional<String> lastName,

    Optional<LocalDate> date,

    @Email
    Optional<String> email,

    Optional<Boolean> active
) {
}
