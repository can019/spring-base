package com.example.base.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public class UnknownException extends RuntimeException implements ErrorResponse {
    private HttpStatus httpStatus;
    private final ProblemDetail body ;
    private final static String DEFAULT_DETAIL = "Unchecked exception(fatal) occurred";
    private final static String TILE = "Unknown exception";
    private final Throwable originCause;

    public UnknownException(String message){
        this(message, null);
    }

    public Throwable getOriginCause() {
        return originCause;
    }

    public UnknownException(Throwable cause){
        this(DEFAULT_DETAIL, cause);
    }

    public UnknownException(String message, Throwable cause){
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
