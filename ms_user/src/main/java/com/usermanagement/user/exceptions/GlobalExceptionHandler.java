package com.usermanagement.user.exceptions;

import com.usermanagement.user.enums.UserRolesEnum;
import com.usermanagement.user.exceptions.build.ErrorCodeEnum;
import com.usermanagement.user.exceptions.build.Problem;
import com.usermanagement.user.exceptions.customException.CPFAlreadyInUseException;
import com.usermanagement.user.exceptions.customException.EmailAlreadyInUseException;
import com.usermanagement.user.exceptions.customException.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handlerUserNotFoundException() {
        StandardCustomException userNotFoundExceptionException = new UserNotFoundException();
        var problem = new Problem(userNotFoundExceptionException.getMessageErrorCode(), userNotFoundExceptionException.getHttpStatus());
        return ResponseEntity.status(userNotFoundExceptionException.getHttpStatus()).body(problem);
    }

    @ExceptionHandler(CPFAlreadyInUseException.class)
    public ResponseEntity<Object> handlerCPFAlreadyInUseException() {
        StandardCustomException cpfAlreadyInUseException = new CPFAlreadyInUseException();
        var problem = new Problem(cpfAlreadyInUseException.getMessageErrorCode(), cpfAlreadyInUseException.getHttpStatus());
        return ResponseEntity.status(cpfAlreadyInUseException.getHttpStatus()).body(problem);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<Object> handlerEmailAlreadyInUseException() {
        StandardCustomException emailAlreadyInUseException = new EmailAlreadyInUseException();
        var problem = new Problem(emailAlreadyInUseException.getMessageErrorCode(), emailAlreadyInUseException.getHttpStatus());
        return ResponseEntity.status(emailAlreadyInUseException.getHttpStatus()).body(problem);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        Problem errorResponse = new Problem(ex.getMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Problem errorResponse = new Problem(ex.getMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handlerConstraintViolationException(ConstraintViolationException ex) {
        var problem = new Problem(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var problem = new Problem(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

}
