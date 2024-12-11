package com.usermanagement.user.model.dto.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestAlterationPassword(

        @NotNull
        @Size(min = 8, message = "Password needs at least 8 characters")
        String password
) {
}
