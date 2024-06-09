package com.example.base.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public final static String RUNTIME_EXCEPTION_MESSAGE = "runtime exception occurred";
    public final static String NOT_FOUND_EXCEPTION_MESSAGE = "not found";
    public final static String EXCEPTION_MESSAGE = "unacceptable exception occurred";
    public final static String HANDLER_HTTP_REQUEST_METHOD_NOT_SUPPORT_MESSAGE = "http method not support";

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ExceptionResult("EX003", "HttpRequestMethodNotSupportedException occurred"));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RUNTIME_EXCEPTION_MESSAGE);
    }

    private record ExceptionResult(String code, String message) {
    }
}
