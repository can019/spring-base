package com.example.base.domain.user.repository;

import com.example.base.BaseApplication;
import com.example.base.domain.user.domain.User;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 임베디드 데이터베이스 사용 x
@Transactional
@SpringBootTest(classes = BaseApplication.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("UserRepository.save()로 유저 생성 후 데이터베이스에 raw query 시 데이터가 존재해야한다")
    public void createUser() {
        // 사용자 저장
        User user = new User();

        User savedUser = userRepository.save(user);

        List<User> userList= em.createQuery("select u from User as u where u.id = :userId", User.class)
                .setParameter("userId", user.getIdAsHexByte()).getResultList();

        assertThat(savedUser.getId()).isEqualTo(user.getId());
        assertThat(userList.size()).isEqualTo(1);
        assertThat(userList.get(0).getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("유저 생성 후 UserRepository.findOneById()로 가져온 데이터는 해당 데이터와 같아야 한다.")
    public void findOneById() {
        User user = new User();

        em.persist(user);
        em.flush();

        User readUser = userRepository.findOneById(user.getId());

        assertThat(readUser).isNotNull();
        assertThat(readUser.getId()).isEqualTo(user.getId());
    }
}
