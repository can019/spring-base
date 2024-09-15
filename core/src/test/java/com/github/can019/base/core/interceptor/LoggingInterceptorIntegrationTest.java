package com.github.can019.base.core.interceptor;

import com.github.can019.base.core.interceptor.resource.LoggingInterceptorTestController;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest
@Import({LoggingInterceptorTestController.class})
public class LoggingInterceptorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void beforeEach() {
        setUptStream();
    }

    @AfterEach
    void afterEach() {
        restoreStream();
    }

    @Test
    @DisplayName("Log level이 trace level일 때 api 요청을 했을 때 loggingInterceptor는 로그를 세 번 찍어야 한다.")
    public void logTestWhenTraceLevel() throws Exception {
        final String getRequestUri = "/test/logging-interceptor/get";
        setLogLevel(Level.TRACE);
        mockMvc.perform(get(getRequestUri));

        List<String> outputList = Arrays.asList(outContent.toString().split("\n"));

        List<String> filteredLogs = outputList.stream()
                .filter(log -> log.contains("LoggingInterceptor"))
                .collect(Collectors.toList());

        assertThat(filteredLogs.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Log level이 info level일 때 api 요청을 했을 때 loggingInterceptor는 로그를 한 번 찍어야 한다.")
    public void logTestWhenInfoLevel() throws Exception {
        final String getRequestUri = "/test/logging-interceptor/get";
        setLogLevel(Level.INFO);
        mockMvc.perform(get(getRequestUri));

        List<String> outputList = Arrays.asList(outContent.toString().split("\n"));

        List<String> filteredLogs = outputList.stream()
                .filter(log -> log.contains("LoggingInterceptor"))
                .collect(Collectors.toList());

        assertThat(filteredLogs.size()).isEqualTo(1);
    }

    public void setLogLevel(Level logLevel) {
        Configurator.setLevel("com.github.can019", logLevel);
    }

    public void setRootLogLevel(Level logLevel){
        Configurator.setRootLevel(logLevel);
    }

    public void setUptStream() {
        System.setOut(new PrintStream(outContent));
    }

    public void restoreStream() {
        System.setOut(originalOut);
    }

    @Configuration
    static class TestConfig implements WebMvcConfigurer {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LoggingInterceptor())
                    .addPathPatterns("/**");
        }
    }
}
