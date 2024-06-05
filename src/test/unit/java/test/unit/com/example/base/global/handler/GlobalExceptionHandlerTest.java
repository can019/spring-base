package test.unit.com.example.base.global.handler;

import com.example.base.global.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.base.global.handler.GlobalExceptionHandler.RUNTIME_EXCEPTION_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

    @Test
    public void 런타임_exception이_발생하면_status_500과_미리_정의된_메세지가_돌아와야한다() {
        // given
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        final String exceptionMessage = "Exception occurred";
        RuntimeException e = new RuntimeException(exceptionMessage);

        // when
        ResponseEntity responseEntity = globalExceptionHandler.runtimeException(e);

        // then
        assertThat(responseEntity.getBody()).isEqualTo(RUNTIME_EXCEPTION_MESSAGE);
        assertThat(responseEntity.getBody()).isNotEqualTo(exceptionMessage);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
