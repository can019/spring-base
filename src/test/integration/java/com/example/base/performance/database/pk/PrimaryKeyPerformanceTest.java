package com.example.base.performance.database.pk;

import com.example.base.performance.database.pk.resource.*;
import com.example.base.test.util.stopwatch.ParallelTestTimeExecutionListener;
import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.util.StopWatch;
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
@TestExecutionListeners(value = {ParallelTestTimeExecutionListener.class}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PrimaryKeyPerformanceTest {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private final static ThreadLocal<EntityManager> entityManagerThreadLocal;

    static {
        entityManagerThreadLocal = new ThreadLocal<EntityManager>();
    }

    private final static int repeatTestTime = 100;

    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.32")
            .withDatabaseName("test");

    @Test
    @DisplayName("JpaAutoIncrement")
    @Execution(ExecutionMode.CONCURRENT)
    public void jpaAutoIncrement() throws Exception{
        insertTest(JpaAutoIncrement.class, "JpaAutoIncrement");
    }

    @Test
    @DisplayName("JpaSequence")
    @Execution(ExecutionMode.CONCURRENT)
    public void jpaSequence() throws Exception{
        insertTest(JpaSequence.class, "JpaSequence");
    }

    @Test
    @DisplayName("UUIDv4")
    @Execution(ExecutionMode.CONCURRENT)
    public void uuidV4() throws Exception{
        insertTest(UUIDv4.class, "UUIDv4");
    }

    @Test
    @DisplayName("UUIDv1")
    @Execution(ExecutionMode.CONCURRENT)
    public void uuidV1() throws Exception{
        insertTest(UUIDv1.class, "UUIDv1");
    }

    @Test
    @DisplayName("UUIDv1 Base Sequential No Hyphen")
    @Execution(ExecutionMode.CONCURRENT)
    public void uuidV1BaseSequentialNoHyphen() throws Exception{
        insertTest(UUIDv1BaseSequentialNoHyphen.class, "UUIDv1 Base Sequential No Hyphen");
    }

    private <T extends PrimaryKeyPerformanceTestEntity> void insertTest(Class<T> clazz, String displayName) throws Exception{
        StopWatch stopWatch = ParallelTestTimeExecutionListener.threadLocalStopWatch.get();
        EntityManager em = getThreadSafeEntityManager();

        for (int i = 0; i < repeatTestTime; i++) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            T entity = clazz.getDeclaredConstructor().newInstance();

            stopWatch.start(displayName + " # " + i);

            em.persist(entity);
            transaction.commit();

            stopWatch.stop();


        }
    }

    private EntityManager getThreadSafeEntityManager(){
        EntityManager em = entityManagerThreadLocal.get();
        if(em == null){
            em = emf.createEntityManager();
            entityManagerThreadLocal.set(em);
        }
        return em;
    }
}
