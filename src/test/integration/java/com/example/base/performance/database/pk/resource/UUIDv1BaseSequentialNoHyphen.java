package com.example.base.performance.database.pk.resource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Profile;

@Entity
@Profile("test")
public class UUIDv1BaseSequentialNoHyphen implements PrimaryKeyPerformanceTestEntity {
    @Id
    @Column(name="ID",columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuidV1")
    @GenericGenerator(
            name="uuidV1",
            strategy = "com.example.base.performance.database.pk.resource.strategy.UUIDv1SequentialNoHyphen"
    )
    public byte[] id;
}
