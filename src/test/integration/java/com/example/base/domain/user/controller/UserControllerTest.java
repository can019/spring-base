package com.example.base.domain.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.base.test.util.WithContextControllerTestBase;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest extends WithContextControllerTestBase {

    @Test
    @DisplayName("유저 생성 (api로 생성 후 데이터 베이스 raw query로 저장 여부 확인), [Post] /user")
    void createUser() throws Exception{
        mockMvc.perform(post("/user")).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }
}
