package com.github.can019.global.handler;

import com.github.can019.global.exception.ApplicationException;
import com.github.can019.global.exception.UnknownException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
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

    /**
     * 응답 body 구성에 필요한 데이터 전처리를 진행.
     *
     * <p>응답 body가 null이라면 ErrorResponse 객체를 가져와 body를 구성</p>
     *
     * @param ex cause
     * @param body body controller에서 작성한 body
     * @param headers
     * @param statusCode controller에서 처리한 statusCode
     * @param request
     * @return
     *
     * @see org.springframework.web.ErrorResponse
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletResponse response = servletWebRequest.getResponse();
            if (response != null && response.isCommitted()) {
                log.warn("Response already committed. Ignoring: {}", ex);
                return null;
            }
        }

        if (statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR) && body == null && !(ex instanceof ErrorResponse)) {
            // 때에 따라 github or slack에 publish?
            // log.error("Fatal error occurred {}", ex.getCause());
            ex = new UnknownException(ex);
        }

        if (body == null && ex instanceof ErrorResponse errorResponse) {
            if(ex instanceof ServletException){
                errorResponse.getBody().setProperty("code", ExceptionCode.SERVLET_WEB_REQUEST_ERROR_CODE.getExceptionCode());
            } else if(ex instanceof ApplicationException){
                errorResponse.getBody().setProperty("code", ExceptionCode.APPLICATION_ERROR_CODE.getExceptionCode());
            } else{
                log.error("[FATAL] {} {}", ex.getMessage(), ex.getStackTrace()[0]);
                errorResponse.getBody().setProperty("code", ExceptionCode.FATAL_ERROR_CODE.getExceptionCode());
            }
            errorResponse.getBody().setProperty("uuid", ThreadContext.get("id"));
            body = errorResponse.updateAndGetBody(getMessageSource(), LocaleContextHolder.getLocale());
        }

        return createResponseEntity(body, headers, statusCode, request);
    }

    private enum ExceptionCode {
        SERVLET_WEB_REQUEST_ERROR_CODE("EX1"),
        APPLICATION_ERROR_CODE("EX2"),
        FATAL_ERROR_CODE("EX3");

        private String exceptionCode;

        ExceptionCode(String exceptionCode){
            this.exceptionCode = exceptionCode;
        }

        public String getExceptionCode(){
            return exceptionCode;
        }
    }
}
