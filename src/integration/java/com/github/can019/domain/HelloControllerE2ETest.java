package com.github.can019.domain;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class})
public class HelloControllerE2ETest {

    @LocalServerPort
    public int port;

    private RequestSpecification spec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        this.spec = new RequestSpecBuilder()
                .addFilter(RestAssuredRestDocumentation.documentationConfiguration(restDocumentation))
                .setPort(port)
                .build();
    }

    @Test
    @DisplayName("Hello get mapping")
    void hello() throws Exception {
        given(this.spec)
                .filter(document("hello"))
                .when()
                .get("/api/v0/hello")
                .then()
                .assertThat()
                .statusCode(200);
    }
}
