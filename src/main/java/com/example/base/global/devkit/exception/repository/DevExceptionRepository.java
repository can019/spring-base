package com.example.base.global.devkit.exception.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("test | local | local_docker | dev")
@Repository
public class DevExceptionRepository {
    @PersistenceContext
    EntityManager em;

    public void exceptionOnRepository(String arg) {
        throw new RuntimeException("on repository");
    }

    public void exceptionOnJpa(String arg) {
        em.createQuery("select * from 8172@&fdj");
    }
}
