package com.example.base.domain.user.controller;

import com.example.base.domain.user.domain.User;
import jakarta.persistence.EntityManager;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.base.test.util.WithContextControllerTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import static com.example.base.global.util.convertor.TypeConvertor.byteArrayToHexString;
import static com.example.base.global.util.convertor.TypeConvertor.hexStringToByte;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        String id = (String) json.get("id");

        User user = em.find(User.class, hexStringToByte(id));
        assertThat(user.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("단일 유저 조회 (데이터 베이스 entityManager로 생성 후 api로 조회) | [Get] /user")
    void getUser() throws Exception{
        User user = new User();
        em.persist(user);
        em.flush();

        mockMvc.perform(get("/user")
                        .param("id", user.getId()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(user.getId()));
    }

    @Test
    @DisplayName("단일 유저 조회 (api로 user 생성 후 api 조회)| [Get] /user -> [Post] /user")
    void createUserAndGetUser() throws Exception{
        MvcResult mvcResult = mockMvc.perform(post("/user")).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty()).andReturn();

        JSONParser jsonParser = new JSONParser();
        JSONObject json = (JSONObject)jsonParser.parse(mvcResult.getResponse().getContentAsString());

        byte[] id = hexStringToByte((String) json.get("id"));

        mockMvc.perform(get("/user")
                        .param("id", byteArrayToHexString(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(json.get("id")));

    }
}
