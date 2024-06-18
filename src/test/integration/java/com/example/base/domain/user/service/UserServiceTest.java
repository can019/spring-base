package com.example.base.domain.user.service;

import com.example.base.domain.user.domain.User;
import com.example.base.domain.user.repository.UserRepository;
import com.example.base.domain.user.service.UserService;
import com.example.base.global.util.convertor.TypeConvertor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("createUser()를 통해 생성 후 fineOneById로 조회")
    void createAndReadUser() {
        User user = new User();
        String savedId = userService.createUser(user);
        userRepository.findOneById(savedId.getBytes());
        assertEquals(savedId, TypeConvertor.byteArrayToHexString(user.getId()));
    }
}
