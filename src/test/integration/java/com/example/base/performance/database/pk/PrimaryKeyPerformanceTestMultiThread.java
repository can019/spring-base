package com.example.base.performance.database.pk;

import com.example.base.performance.database.pk.resource.*;
import com.example.base.test.annotation.ExtremeSlow;
import com.example.base.test.util.ParallelTestTimeExecutionListener;
import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.util.StopWatch;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@ActiveProfiles("silence")
@Testcontainers
@TestExecutionListeners(value = {ParallelTestTimeExecutionListener.class}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PrimaryKeyPerformanceMultiThreadInternal.class)
@ExtremeSlow
public class PrimaryKeyPerformanceTestMultiThread {

    @Autowired
    PrimaryKeyPerformanceMultiThreadInternal internal;


    private final static ThreadLocal<EntityManager> entityManagerThreadLocal;

    static {
        entityManagerThreadLocal = new ThreadLocal<EntityManager>();
    }

    private final static int repeatTestTime = 10;

    @DynamicPropertySource
    static void hikariPool(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.hikari.minimum-idle", ()-> 11);
        registry.add("spring.datasource.hikari.maximum-pool-size", ()-> 11);
    }

    @DynamicPropertySource
    static void silenceLog4j2(DynamicPropertyRegistry registry) {
        String yamlFilePath = "src/main/resources/log4j2-silence.yml";
        registry.add("log4j2.configurationFile", ()->"file:" + yamlFilePath);
    }

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

        for (int i = 0; i < repeatTestTime; i++) {
            T entity = clazz.getDeclaredConstructor().newInstance();

            stopWatch.start(displayName + " # " + i);

            internal.persistEntity(entity);

            stopWatch.stop();
        }
    }

}
