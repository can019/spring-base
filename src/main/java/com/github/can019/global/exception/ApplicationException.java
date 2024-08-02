package com.github.can019.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

/**
 * <a href=https://datatracker.ietf.org/doc/html/rfc7807>RFC 7870</a>에 맞는 error response를 제공할 수 있는 exception
 *
 * <p>생성 시 ProblemDetail을 초기화. GlobalExceptionHandler에서 ProblemDatail을 사용하여 body 구성.
 * 프로젝트 내에서 새로운 exception을 정의하고자 할 때 해당 exception을 extedns하여 작성하면 됨.
 *
 * jakarta.servlet.http.HttpServletResponse을 참조하여 작성함.</p>
 *
 * @see <a href=https://datatracker.ietf.org/doc/html/rfc7807>RFC 7870 document</a>
 * @see com.github.can019.global.handler.GlobalExceptionHandler
 * @see org.springframework.http.ProblemDetail
 * @see jakarta.servlet.http.HttpServletResponse
 * @since 0.0.3
 */
public class ApplicationException extends RuntimeException implements ErrorResponse {
    private HttpStatus httpStatus;
    private final ProblemDetail body ;
    private final static String DEFAULT_DETAIL = "Application exception occurred";
    private final static String TILE = "Application exception";
    private final Throwable originCause;

    public ApplicationException(String message){
        this(message, null);
    }

    public ApplicationException(Throwable cause){
        this(DEFAULT_DETAIL, cause);
    }

    /**
     * ApplicationException의 생성자
     *
     * <p>다른 생성자들은 해당 생성자를 호출.
     * HttpStatus는 500으로 생성</p>
     *
     * @param message Exception message
     * @param cause 상위 cause
     */
    public ApplicationException(String message, Throwable cause){
        super(message, cause);
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
