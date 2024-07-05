package com.example.base.performance.database.pk.resource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Entity
@Profile({"test" , "silence"})
public class UUIDv1BaseSequential implements PrimaryKeyPerformanceTestEntity {
    @Id
    @Column(name="ID",columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuidV1_sequential")
    @GenericGenerator(
            name="uuidV1_sequential",
            strategy = "com.example.base.performance.database.pk.resource.strategy.UUIDv1Sequential"
    )
    public UUID id;
}
