package com.example.base.domain.user.controller;

import com.example.base.domain.user.domain.User;
import com.example.base.global.util.convertor.TypeConvertor;
import jakarta.persistence.EntityManager;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.base.test.util.WithContextControllerTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest extends WithContextControllerTestBase {
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("유저 생성 (api로 생성 후 entityManager로 저장 여부 확인), [Post] /user")
    void createUser() throws Exception{
        MvcResult mvcResult = mockMvc.perform(post("/user")).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty()).andReturn();

        JSONParser jsonParser = new JSONParser();
        JSONObject json = (JSONObject)jsonParser.parse(mvcResult.getResponse().getContentAsString());

        byte[] id = TypeConvertor.hexStringToByte((String) json.get("id"));

        User user = em.find(User.class, id);
        assertThat(user.getId()).isEqualTo(id);
    }
}
