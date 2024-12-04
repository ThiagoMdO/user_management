package com.usermanagement.user.exceptions.customException;

import com.usermanagement.user.exceptions.StandardCustomException;
import com.usermanagement.user.exceptions.build.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class UserCannotBeChangedException extends StandardCustomException {

    public UserCannotBeChangedException() {
        super(ErrorCodeEnum.THE_USER_CANNOT_BE_CHANGED, HttpStatus.BAD_REQUEST);
    }
}
