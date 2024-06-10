package com.example.base.global.handler;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.base.global.handler.GlobalExceptionHandlerTestController.APPLICATION_EXCEPTION_URI;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.example.base.global.handler.GlobalExceptionHandlerTestController.WRONG_METHOD_URI_WHEN_USE_GET;

public class GlobalExceptionHandlerTest {
    @Nested
    class SingleThread {
        @Nested
        @WebMvcTest(controllers = GlobalExceptionHandlerTestController.class,
                properties = {"spring.profiles.active=test"})
        @ExtendWith(MockitoExtension.class)
        class WithControllerByWebMvcTest{
            @Autowired
            private MockMvc mockMvc;

            @Test
            void 잘못된_http_method_호출_시_status_405와_message가_와야한다() throws Exception {
                mockMvc.perform(get(WRONG_METHOD_URI_WHEN_USE_GET))
                        .andExpect(status().isMethodNotAllowed());
            }

            @Test
            void ApplicationException이_발생했을_때_response는_RFC7807를_준수해야한다() throws Exception {
                mockMvc.perform(get(APPLICATION_EXCEPTION_URI))
                        .andExpect(jsonPath("$.type").isNotEmpty())
                        .andExpect(jsonPath("$.title").isNotEmpty())
                        .andExpect(jsonPath("$.status").isNotEmpty())
                        .andExpect(jsonPath("$.detail").isNotEmpty());
            }

            @Test
            void ApplicationException이_발생했을_때_response의_code는_EX2여야한다() throws Exception {
                mockMvc.perform(get(APPLICATION_EXCEPTION_URI))
                        .andExpect(jsonPath("$.code").isNotEmpty())
                        .andExpect(jsonPath("$.code").value("EX2"));
            }

            @Test
            void ResponseEntityExceptionHandler에_정의된_모든_exeption을_제대로_처리해야한다() throws Exception {

            }
        }
    }

}
