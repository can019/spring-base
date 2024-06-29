package com.example.base.performance.database.pk;

import com.example.base.performance.database.pk.resource.*;
import com.example.base.test.util.TestTimeExecutionListener;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest(properties = {"spring.profiles.active=test",
        "logging.level.org.springframework=ERROR",
        "logging.level.com.example.base=ERROR",
        "spring.main.banner-mode=off",
        "logging.level.root=ERROR",
        "spring.jpa.properties.hibernate.show_sql=false",
        "spring.jpa.properties.hibernate.use_sql_comments=false",
        "spring.jpa.properties.hibernate.highlight_sql=false",
        "logging.level.org.hibernate.SQL=OFF",
        "logging.level.org.hibernate.orm.jdbc.bind=OFF",
})
@ActiveProfiles("test")
@Testcontainers
@TestExecutionListeners(value = {TestTimeExecutionListener.class}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
public class PrimaryKeyPerformanceTestLegacy {

    @PersistenceContext
    private EntityManager em;

    private final static int repeatTestTime = 100;

    @Container
    static MySQLContainer<?> mysqlContainer= new MySQLContainer<>("mysql:8.0.32")
            .withDatabaseName("test");

    @RepeatedTest(value = repeatTestTime, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("JpaAutoIncrement")
    void jpaAutoIncrement() {
        JpaAutoIncrement jpaAutoIncrement = new JpaAutoIncrement();
        em.persist(jpaAutoIncrement);
    }

    @RepeatedTest(value = repeatTestTime, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("JpaSequence")

    void jpaSequence() {
        JpaSequence jpaSequence = new JpaSequence();
        em.persist(jpaSequence);
    }

    @RepeatedTest(value = repeatTestTime, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("UUIDv4")
    void uuidV4() {
        UUIDv4 uuiDv4 = new UUIDv4();
        em.persist(uuiDv4);
    }

    @RepeatedTest(value = repeatTestTime, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("UUIDv1")
    void uuidV1() {
        UUIDv1 uuiDv1 = new UUIDv1();
        em.persist(uuiDv1);
    }

    @RepeatedTest(value = repeatTestTime, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("UUIDv1 Base Sequential No Hyphen")
    void uuidV1BaseSequentialNoHyphen() {
        UUIDv1BaseSequentialNoHyphen uuiDv1BaseSequentialNoHyphen = new UUIDv1BaseSequentialNoHyphen();
        em.persist(uuiDv1BaseSequentialNoHyphen);
    }
}
