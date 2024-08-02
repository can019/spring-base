package com.github.can019.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

/**
 * Fatal exception
 *
 * <p>예측하지 못한 Exception. 발생시점은 알지만 이유는 명확히 모르는 경우등 보통의 경우라면
 * ApplicationException을 쓰는 것을 권고.</p>
 *
 * @See ApplicationException
 * @Since 0.0.3
 */
public class UnknownException extends RuntimeException implements ErrorResponse {
    private HttpStatus httpStatus;
    private final ProblemDetail body ;
    private final static String DEFAULT_DETAIL = "Unchecked exception(fatal) occurred";
    private final static String TILE = "Unknown exception";

    public UnknownException(String message){
        this(message, null);
    }

    public UnknownException(Throwable cause){
        this(DEFAULT_DETAIL, cause);
    }

    public UnknownException(String message, Throwable cause){
        super(message, cause);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.body = ProblemDetail.forStatusAndDetail(getStatusCode(), message);
        this.body.setTitle(DEFAULT_DETAIL);

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
