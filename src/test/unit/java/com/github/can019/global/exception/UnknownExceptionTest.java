package com.example.base.global.exception;

import com.github.can019.global.exception.UnknownException;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class UnknownExceptionTest {
    @Nested
    class SingleThread {
        @Nested
        @DisplayName("UnknownException(String message) 생성자로 생성한 경우")
        class ExceptionWithMessage {
            private final String exceptionMessageForWithMessage = "withMessage";
            UnknownException unknownExceptionWithMessage;

            @BeforeEach()
            void beforeEach() {
                unknownExceptionWithMessage = new UnknownException(exceptionMessageForWithMessage);
            }

            @Test
            void 생성_시_status_code_는_500이어야한다() {
                Assertions.assertAll(
                        () -> assertThat(unknownExceptionWithMessage.getBody().getStatus()).isEqualTo(500),
                        () -> assertThat(unknownExceptionWithMessage.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                );
            }

            @Test
            void Message만으로_생성_시_detail은_message여야_한다() {
                assertThat(unknownExceptionWithMessage.getBody().getDetail()).isEqualTo(exceptionMessageForWithMessage);
            }

            @Test
            void Message만으로_생성_시_Body의_instance는_비어있어야한다() {
                assertThat(unknownExceptionWithMessage.getBody().getInstance()).isNull();
            }
        }

        @Nested
        @DisplayName("UnknownException(Throwable cause) 생성자로 생성한 경우")
        class ExceptionWithOriginException {
            private final String exceptionMessageForWithException = "withException";
            private final String exceptionMessageForRuntimeException = "Origin";

            UnknownException unknownExceptionWithException;
            RuntimeException runtimeException1;
            RuntimeException runtimeException2;


            @BeforeEach()
            void beforeEach() {
                runtimeException1 = new RuntimeException();
                runtimeException2 = new RuntimeException(exceptionMessageForRuntimeException);
                unknownExceptionWithException = new UnknownException(exceptionMessageForWithException);

            }

            @Test
            void 생성_시_status_code_는_500이어야한다() {
                Assertions.assertAll(
                        () -> assertThat(unknownExceptionWithException.getBody().getStatus()).isEqualTo(500),
                        () -> assertThat(unknownExceptionWithException.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                );
            }

            @Test
            void Exception으로_생성_시_detail은_null이면_안된다() {
                assertThat(unknownExceptionWithException.getBody().getDetail()).isNotNull();
            }

            @Test
            void Exception으로_생성_시_Body의_instance는_비어있어야한다() {
                assertThat(unknownExceptionWithException.getBody().getInstance()).isNull();
            }
        }

        @Nested
        @DisplayName("UnknownException(String message, Throwable Cause) 생성자로 생성한 경우")
        class ExceptionWithMessageAndOriginException {
            private final String exceptionMessageForRuntimeException = "Origin";
            private final String exceptionMessageForWithMessageAndException = "withMessageAndException";

            UnknownException unknownExceptionWithMessageAndException;
            RuntimeException runtimeException1;
            RuntimeException runtimeException2;


            @BeforeEach()
            void beforeEach() {
                runtimeException1 = new RuntimeException();
                runtimeException2 = new RuntimeException(exceptionMessageForRuntimeException);

                unknownExceptionWithMessageAndException =
                        new UnknownException(exceptionMessageForWithMessageAndException, runtimeException2);

            }

            @Test
            void 생성_시_status_code_는_500이어야한다() {
                Assertions.assertAll(
                        () -> assertThat(unknownExceptionWithMessageAndException.getBody().getStatus()).isEqualTo(500),
                        () -> assertThat(unknownExceptionWithMessageAndException.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                );
            }

            @Test
            void Message와_exception으로_생성_시_detail은_message여야_한다() {
                assertThat(unknownExceptionWithMessageAndException.getBody().getDetail()).isEqualTo(exceptionMessageForWithMessageAndException);
            }
        }
    }
}
