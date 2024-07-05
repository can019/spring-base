package com.example.base.performance.database.pk.resource;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Entity
@Profile({"test" , "silence"})
public class UUIDv4 implements PrimaryKeyPerformanceTestEntity {
    @Id
    @Column(name="ID",columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuidV4")
    @GenericGenerator(
            name="uuidV4",
            strategy = "com.example.base.performance.database.pk.resource.strategy.UUIDv4"
    )
    public UUID id;
}
