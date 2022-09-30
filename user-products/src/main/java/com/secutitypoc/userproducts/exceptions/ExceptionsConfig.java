package com.secutitypoc.userproducts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionsConfig {
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<?> dataNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
    }

    @ExceptionHandler({InvalidRequestException.class})
    public ResponseEntity<?> invalidRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
    }
}
