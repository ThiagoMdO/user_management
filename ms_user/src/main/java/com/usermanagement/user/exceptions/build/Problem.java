package com.usermanagement.user.exceptions.build;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class Problem {

    private final int code;

    private final String status;

    private final String message;

    public Problem(String message, HttpStatus status) {
        this.code = status.value();
        this.status = status.name();
        this.message = message;
    }

    public Problem(ConstraintViolationException violationException) {
        this.code = HttpStatus.BAD_REQUEST.value();
        this.status = HttpStatus.BAD_REQUEST.name();
        this.message = formatViolationMessages(violationException.getConstraintViolations());
    }

    public Problem(MethodArgumentNotValidException argumentNotValidException) {
        this.code = HttpStatus.BAD_REQUEST.value();
        this.status = HttpStatus.BAD_REQUEST.name();
        this.message = Arrays.toString(argumentNotValidException.getDetailMessageArguments());
    }

    public Problem(String defaultMessage) {
        this.code = HttpStatus.BAD_REQUEST.value();
        this.status = HttpStatus.BAD_REQUEST.name();
        this.message = defaultMessage;
    }

    private String formatViolationMessages(Set<ConstraintViolation<?>> violations) {
        return violations.stream()
        .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
        .collect(Collectors.joining(", "));
    }
}
