package com.github.can019.base.test.dependency.testcontainer;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("test")
@Testcontainers
public class TestContainerMysqlTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("TestContainer mysql과 연동되어야 한다")
    void checkUsingMysqlContainer() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            assertThat(databaseProductName).isEqualTo("MySQL");
        }
    }
}
