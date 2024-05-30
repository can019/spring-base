package com.example.base.domain.user.service;

import com.example.base.domain.user.domain.User;
import com.example.base.domain.user.repository.UserRepository;
import com.example.base.global.util.convertor.TypeConvertor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    void 유저생성() {
        User user = new User();
        String savedId = userService.createUser(user);
        userRepository.findOne(savedId.getBytes());
        Assertions.assertEquals(savedId, TypeConvertor.byteArrayToHexString(user.getId()));
    }
}
