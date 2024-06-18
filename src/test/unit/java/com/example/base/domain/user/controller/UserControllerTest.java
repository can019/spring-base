package com.example.base.domain.user.controller;

import com.example.base.domain.user.domain.User;
import com.example.base.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.base.global.util.convertor.TypeConvertor.hexStringToByte;
import static com.example.base.global.util.generator.UUID.generateSequentialUUIDV1WithoutHyphen;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("User 생성 시 status는 201이며 응답 시 id가 올바르게 와야 한다 | [Post] /user")
    void createUser() throws Exception{
        String strId = generateSequentialUUIDV1WithoutHyphen();
        User user = mock(User.class);

        when(userService.createUser(any())).thenReturn(strId);
        mockMvc.perform(post("/user").param("id", strId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(strId))
                .andDo(print());
    }

    @Test
    @DisplayName("User 조회 시 status는 200이며 응답 시 id가 올바르게 와야 한다 | [Get] /user")
    void findOneById() throws Exception{
        String strId = generateSequentialUUIDV1WithoutHyphen();
        byte[] id = hexStringToByte(strId);
        User user = mock(User.class);

        when(user.getIdAsString()).thenReturn(strId);
        when(userService.findOneById(strId)).thenReturn(user);

        mockMvc.perform(get("/user").param("id", strId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(strId))
                .andDo(print());
    }
}
