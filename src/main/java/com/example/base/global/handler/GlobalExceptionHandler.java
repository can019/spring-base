package com.example.base.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    public final static String RUNTIME_EXCEPTION_MESSAGE = "unacceptable exception occurred";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RUNTIME_EXCEPTION_MESSAGE);
    }
}
