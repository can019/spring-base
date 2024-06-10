package test.unit.com.example.base.global.exception;

import com.example.base.global.exception.ApplicationException;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

public class ApplicationExceptionTest {
    @Nested
    class SingleThread {
        @Nested
        @DisplayName("ApplicationException(String message) 생성자로 생성한 경우")
        class ExceptionWithMessage {
            private final String exceptionMessageForWithMessage = "withMessage";
            ApplicationException applicationExceptionWithMessage;
            @BeforeEach()
            void beforeEach(){
                applicationExceptionWithMessage = new ApplicationException(exceptionMessageForWithMessage);
            }

            @Test
            void 생성_시_status_code_는_500이어야한다(){
                Assertions.assertAll(
                        ()-> assertThat(applicationExceptionWithMessage.getBody().getStatus()).isEqualTo(500),
                        ()-> assertThat(applicationExceptionWithMessage.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                );
            }

            @Test
            void Message만으로_생성_시_detail은_message여야_한다(){
                assertThat(applicationExceptionWithMessage.getBody().getDetail()).isEqualTo(exceptionMessageForWithMessage);
            }

            @Test
            void Message만으로_생성_시_Body의_instance는_비어있어야한다(){
                assertThat(applicationExceptionWithMessage.getBody().getInstance()).isNull();
            }
        }

        @Nested
        @DisplayName("ApplicationException(Throwable cause) 생성자로 생성한 경우")
        class ExceptionWithOriginException {
            private final String exceptionMessageForWithException = "withException";
            private final String exceptionMessageForRuntimeException = "Origin";

            ApplicationException applicationExceptionWithException;
            RuntimeException runtimeException1;
            RuntimeException runtimeException2;


            @BeforeEach()
            void beforeEach(){
                runtimeException1 = new RuntimeException();
                runtimeException2 = new RuntimeException(exceptionMessageForRuntimeException);
                applicationExceptionWithException = new ApplicationException(exceptionMessageForWithException);

            }

            @Test
            void 생성_시_status_code_는_500이어야한다(){
                Assertions.assertAll(
                        ()-> assertThat(applicationExceptionWithException.getBody().getStatus()).isEqualTo(500),
                        ()-> assertThat(applicationExceptionWithException.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                );
            }

            @Test
            void Exception으로_생성_시_detail은_null이면_안된다(){
                assertThat(applicationExceptionWithException.getBody().getDetail()).isNotNull();
            }

            @Test
            void Exception으로_생성_시_Body의_instance는_비어있어야한다(){
                assertThat(applicationExceptionWithException.getBody().getInstance()).isNull();
            }
        }

        @Nested
        @DisplayName("ApplicationException(String message, Throwable Cause) 생성자로 생성한 경우")
        class ExceptionWithMessageAndOriginException {
            private final String exceptionMessageForRuntimeException = "Origin";
            private final String exceptionMessageForWithMessageAndException = "withMessageAndException";

            ApplicationException applicationExceptionWithMessageAndException;
            RuntimeException runtimeException1;
            RuntimeException runtimeException2;


            @BeforeEach()
            void beforeEach(){
                runtimeException1 = new RuntimeException();
                runtimeException2 = new RuntimeException(exceptionMessageForRuntimeException);

                applicationExceptionWithMessageAndException =
                        new ApplicationException(exceptionMessageForWithMessageAndException, runtimeException2);

            }

            @Test
            void 생성_시_status_code_는_500이어야한다(){
                Assertions.assertAll(
                        ()-> assertThat(applicationExceptionWithMessageAndException.getBody().getStatus()).isEqualTo(500),
                        ()-> assertThat(applicationExceptionWithMessageAndException.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                );
            }

            @Test
            void Message와_exception으로_생성_시_detail은_message여야_한다(){
                assertThat(applicationExceptionWithMessageAndException.getBody().getDetail()).isEqualTo(exceptionMessageForWithMessageAndException);
            }
        }
    }
}
