package com.example.base.global.handler;

import com.example.base.global.exception.ApplicationException;
import com.example.base.global.exception.UnknownException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException ex, WebRequest webRequest) {
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, null, headers, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }

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
            } else if(ex instanceof  ApplicationException){
                errorResponse.getBody().setProperty("code", ExceptionCode.APPLICATION_ERROR_CODE.getExceptionCode());
            } else{
                errorResponse.getBody().setProperty("code", ExceptionCode.FATAL_ERROR_CODE.getExceptionCode());
            }
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
