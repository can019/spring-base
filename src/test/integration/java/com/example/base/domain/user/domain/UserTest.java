package com.example.base.domain.user.domain;

import com.example.base.global.identifier.Identifier;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserTest {

    @Autowired
    TestEntityManager testEntityManager;

    EntityManager em;

    @BeforeEach
    void beforeEach() {
        this.em = testEntityManager.getEntityManager();
    }

    @Test
    @DisplayName("User persist 후 getIdentifier()과 체이닝된 메소드들은 null을 return하면 안된다")
    void test() {
        User createUser = new User();
        em.persist(createUser);
        em.flush();
        em.clear();

        assertThat(createUser.getIdentifier()).isNotNull();
        assertThat(createUser.getIdentifier().getIdAsString()).isNotNull();
        assertThat(createUser.getIdentifier().getIdAsByte()).isNotNull();
    }

    @Test
    @DisplayName("User 생성 후 영속성 컨텍스트를 초기화 후 생성된 유저를 조회했을 때 제대로 조회되어야한다.")
    void test2() {
        User createUser = new User();
        em.persist(createUser);
        em.flush();
        em.clear();

        Identifier identifier = createUser.getIdentifier();

        User readUser = em.find(User.class, createUser.getIdentifier().getIdAsByte());

        assertThat(readUser.getIdentifier()).isEqualTo(identifier);
    }
}
