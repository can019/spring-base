package com.example.base.global.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.base.global.handler.GlobalExceptionHandlerTestController.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GlobalExceptionHandlerTest {
    @Nested
    class SingleThread {
        @Nested
        @DisplayName("ServletException (Only test HttpRequestMethodNotSupportedException )")
        class ServletExceptionTest {
            @Nested
            @DisplayName("HttpRequestMethodNotSupportedException")
            @WebMvcTest(controllers = GlobalExceptionHandlerTestController.class,
                    properties = {"spring.profiles.active=test"})
            @ExtendWith(MockitoExtension.class)
            class HttpRequestMethodNotSupportedExceptionTest{
                @Autowired
                private MockMvc mockMvc;

                @Test
                void status_405와_message가_와야한다() throws Exception {
                    mockMvc.perform(get(WRONG_METHOD_URI_WHEN_USE_GET))
                            .andExpect(status().isMethodNotAllowed());
                }

                @Test
                void Response는_RFC7807를_준수해야한다() throws Exception {
                    mockMvc.perform(get(WRONG_METHOD_URI_WHEN_USE_GET))
                            .andExpect(jsonPath("$.type").isNotEmpty())
                            .andExpect(jsonPath("$.title").isNotEmpty())
                            .andExpect(jsonPath("$.status").isNotEmpty())
                            .andExpect(jsonPath("$.detail").isNotEmpty());
                }

                @Test
                void Response의_code는_EX2여야한다() throws Exception {
                    mockMvc.perform(get(APPLICATION_EXCEPTION_URI))
                            .andExpect(jsonPath("$.code").isNotEmpty())
                            .andExpect(jsonPath("$.code").value("EX2"));
                }
            }
        }

        @Nested
        @DisplayName("CustomException")
        class CustomExceptionTest {

            @Nested
            @DisplayName("ApplicationException")
            @WebMvcTest(controllers = GlobalExceptionHandlerTestController.class,
                    properties = {"spring.profiles.active=test"})
            @ExtendWith(MockitoExtension.class)
            class ApplicationExceptionTest {
                @Autowired
                private MockMvc mockMvc;

                @Test
                void Response는_RFC7807를_준수해야한다() throws Exception {
                    mockMvc.perform(get(APPLICATION_EXCEPTION_URI))
                            .andExpect(jsonPath("$.type").isNotEmpty())
                            .andExpect(jsonPath("$.title").isNotEmpty())
                            .andExpect(jsonPath("$.status").isNotEmpty())
                            .andExpect(jsonPath("$.detail").isNotEmpty());
                }

                @Test
                void Response의_code는_EX2여야한다() throws Exception {
                    mockMvc.perform(get(APPLICATION_EXCEPTION_URI))
                            .andExpect(jsonPath("$.code").isNotEmpty())
                            .andExpect(jsonPath("$.code").value("EX2"));
                }
            }
        }

        @Nested
        @DisplayName("RuntimeException")
        @WebMvcTest(controllers = GlobalExceptionHandlerTestController.class,
                properties = {"spring.profiles.active=test"})
        @ExtendWith(MockitoExtension.class)
        class RuntimeExceptionTest {
            @Autowired
            private MockMvc mockMvc;

            @Test
            void Response는_RFC7807를_준수해야한다() throws Exception {
                mockMvc.perform(get(RUNTIME_EXCEPTION_URI))
                        .andExpect(jsonPath("$.type").isNotEmpty())
                        .andExpect(jsonPath("$.title").isNotEmpty())
                        .andExpect(jsonPath("$.status").isNotEmpty())
                        .andExpect(jsonPath("$.detail").isNotEmpty());
            }
        }
    }

}
