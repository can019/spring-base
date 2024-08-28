package com.github.can019.base.global.test.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.can019.base.BaseApplication;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = BaseApplication.class)
@Disabled
// Servlet container mocking
// @SpringTest와 WebMvc를 사용하는 경우 사용. @WebMvcTest과 함께 쓰면 각자 서로의 MockMvc를 모킹하려 하기 때문에 충돌
// @WebMvc는 타겟 클래스만 인스턴스화 하지만 @AuthConfigureMockMvc는 관련 service, repository 모두 인스턴스화.
@AutoConfigureMockMvc
@Transactional
public abstract class WithContextControllerTestBase {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
