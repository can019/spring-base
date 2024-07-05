package com.example.base.performance.database.pk;

import com.example.base.performance.database.pk.resource.PrimaryKeyPerformanceTestEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Profile("test")
public class PrimaryKeyPerformanceMultiThreadInternal {
    @PersistenceContext
    EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Commit
    public void persistEntity(PrimaryKeyPerformanceTestEntity entity){
        em.persist(entity);
        em.flush();
    }
}
