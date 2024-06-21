package com.example.base.global.handler;

import com.example.base.global.exception.ApplicationException;
import com.example.base.global.exception.UnknownException;
import com.example.base.global.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.*;

public class GlobalExceptionHandlerTest {
    @Nested
    class SingleThread{
        @Mock
        HttpHeaders httpHeaders;

        @Mock
        WebRequest webRequest;

        @Mock
        HttpStatus givenHttpStatus;

        @Test
        public void RuntimeException이_주어진_경우_직접적으로_핸들링이_가능해야한다() {
            // given
            GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
            final String exceptionMessage = "Exception occurred";
            RuntimeException e = new RuntimeException(exceptionMessage);

            // when
            ResponseEntity responseEntity = globalExceptionHandler.handleRuntimeException(e, webRequest);

            // then
            assertThat(responseEntity.getBody()).isNotNull();
        }

        @Test
        void ApplicationException이_주어진_경우_직접적으로_핸들링이_가능해야한다() throws Exception{
            GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
            ApplicationException e = new ApplicationException("");

            ResponseEntity responseEntity = globalExceptionHandler.handleRuntimeException(e, webRequest);

            assertThat(responseEntity.getBody()).isNotNull();
        }

        @Test
        void UnknownException이_주어진_경우_직접적으로_핸들링이_가능해야한다() throws Exception{
            GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
            UnknownException e = new UnknownException("");


            ResponseEntity responseEntity = globalExceptionHandler.handleRuntimeException(e, webRequest);

            assertThat(responseEntity.getBody()).isNotNull();
        }
    }
}
