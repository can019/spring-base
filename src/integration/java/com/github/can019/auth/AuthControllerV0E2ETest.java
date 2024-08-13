package com.github.can019.auth;


import com.github.can019.test.util.url.BaseUrl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import net.datafaker.Faker;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class AuthControllerV0E2ETest {
    private Faker faker;

    @LocalServerPort
    private int port;

    private final String authControllerBaseUrl = "/api/v0/auth";

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = BaseUrl.LOCALHOST_URL.getUrl();

    @BeforeEach
    void beforeEach() {
        this.faker = new Faker(Locale.KOREA);
    }

    @Test
    @DisplayName("Registry")
    void registryTest() {
        String url = baseUrl + ":" + port + authControllerBaseUrl + "/registry";
        // 요청 Body 데이터
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username",faker.internet().username());
        requestBody.put("email", faker.internet().safeEmailAddress());
        requestBody.put("password", faker.internet().password(6, 15));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        // 요청 엔터티 생성
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // 응답 상태 코드 검증
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

}
