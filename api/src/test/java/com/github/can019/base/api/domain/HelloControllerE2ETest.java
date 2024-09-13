package com.github.can019.base.api.domain;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
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
@ExtendWith(RestDocumentationExtension.class)
public class HelloControllerE2ETest {

    @LocalServerPort
    public int port;

    private RequestSpecification spec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
//        RestAssured.port = port;
        this.spec = new RequestSpecBuilder()
                .addFilter(RestAssuredRestDocumentation.documentationConfiguration(provider))
                .setPort(port)
                .build();
    }

    @Test
    @DisplayName("Hello get mapping")
    void hello() throws Exception {
        given(this.spec)
                .filter(RestAssuredRestDocumentationWrapper.document("hello","[Get] hello"))
                .when()
                .get("/api/v0/hello")
                .then()
                .assertThat()
                .statusCode(200);
    }
}
