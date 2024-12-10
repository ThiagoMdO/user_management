package com.usermanagement.user.exceptions.customException;

import com.usermanagement.user.exceptions.StandardCustomException;
import com.usermanagement.user.exceptions.build.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends StandardCustomException {

    public UserNotFoundException() {
        super(ErrorCodeEnum.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
