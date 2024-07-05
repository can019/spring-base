package com.example.base.performance.database.pk.resource;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.context.annotation.Profile;

@Entity
@Profile({"test" , "silence"})
public class JpaAutoIncrement implements PrimaryKeyPerformanceTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
}
