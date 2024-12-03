package com.usermanagement.user.exceptions.build;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCodeEnum {
    NOT_FOUND("Data not found"),

    USER_NOT_FOUND("User not found"),

    EMAIL_ALREADY_IN_USE("Email already in use"),

    CPF_ALREADY_IN_USE("CPF already in use"),

    BAD_REQUEST("Invalid data"),

    NOT_ALL0WED_CHANGE_PASSWORD_FROM_OTHER_USER("not allowed to change password from other user"),

    SYSTEM_ERROR("Unavailable server"),

    TOKEN_EXPIRATED("The Token has expired on ");

    private final String message;
}
