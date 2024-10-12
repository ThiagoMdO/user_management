package com.usermanagement.user.exceptions.customException;

import com.usermanagement.user.exceptions.StandardCustomException;
import com.usermanagement.user.exceptions.build.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class EmailAlreadyInUseException extends StandardCustomException {

    public EmailAlreadyInUseException() {
        super(ErrorCodeEnum.EMAIL_ALREADY_IN_USE, HttpStatus.CONFLICT);
    }
}
