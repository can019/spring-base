package com.github.can019.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Application에서 발생하는 모든 exception을 처리하는 handler.
 *
 * @since 0.0.3
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException ex, WebRequest webRequest) {
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, null, headers, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }
}
