package com.example.base.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;


public class ApplicationException extends RuntimeException implements ErrorResponse {
    private HttpStatus httpStatus;
    private final ProblemDetail body ;
    private final static String DEFAULT_DETAIL = "Unaccepted exception occurred in application";
    private final static String TILE = "Application exception";
    private final Throwable originCause;

    public ApplicationException(String message){
        this(message, null);
    }

    public ApplicationException(Throwable cause){
        this(DEFAULT_DETAIL, cause);
    }

    public ApplicationException(String message, Throwable cause){
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.body = ProblemDetail.forStatusAndDetail(getStatusCode(), message);
        this.body.setTitle(DEFAULT_DETAIL);
        this.originCause = cause;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return this.httpStatus;
    }

    @Override
    public ProblemDetail getBody() {
        return this.body;
    }
}
