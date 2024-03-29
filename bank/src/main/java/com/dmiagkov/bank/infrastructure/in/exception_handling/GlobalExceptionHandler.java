package com.dmiagkov.bank.infrastructure.in.exception_handling;

import com.dmiagkov.bank.application.service.exceptions.UserIsNotExistException;
import com.dmiagkov.bank.infrastructure.in.exception_handling.exceptions.user.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String EXCEPTION_MESSAGE = "EXCEPTION OCCURRED: ";

    @ExceptionHandler({EmailIsNotUniqueException.class, IsOnlyOneEmailException.class,
            IsOnlyOnePhoneException.class, LoginIsNotUniqueException.class,
            PhoneIsNotUniqueException.class})
    public ResponseEntity<String> handleException(RuntimeException exception) {
        log.error(EXCEPTION_MESSAGE, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler()
    public ResponseEntity<String> handleException(UserIsNotExistException exception) {
        log.error(EXCEPTION_MESSAGE, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler()
    public ResponseEntity<String> handleException(Exception exception) {
        log.error(EXCEPTION_MESSAGE, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
