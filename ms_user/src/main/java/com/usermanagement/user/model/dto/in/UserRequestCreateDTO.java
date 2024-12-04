package com.usermanagement.user.model.dto.in;

import com.usermanagement.user.model.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

public record UserRequestCreateDTO(
    @Size(min = 2, message = "Fist name needs at least 2 characters")
    String firstName,

    @Size(min = 2, message = "Last Name needs at least 2 characters")
    String lastName,

    @CPF
    String cpf,

    @NotNull(message = "The date can't be null")
    LocalDate date,

    @Email
    String email,

    @NotNull
    @Size(min = 8, message = "Password needs at least 8 characters")
    String password,

    Boolean active,

    List<Role> roles
){}
