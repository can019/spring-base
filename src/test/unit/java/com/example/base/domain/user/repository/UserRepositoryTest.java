package com.example.base.domain.user.repository;

import com.example.base.domain.user.domain.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 임베디드 데이터베이스 사용 x
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager em;


    @Test
    @DisplayName("유저 생성 | userRepository.save()")
    public void createUser() {
        // 사용자 저장
        User user = new User();

        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isEqualTo(user.getId());

    }
}
