package com.example.base.performance.database.pk.resource;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Entity
@Profile("test")
public class UUIDv1 {
    @Id
    @Column(name="ID",columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuidV1")
    @GenericGenerator(
            name="uuidV1",
            strategy = "com.example.base.performance.database.pk.resource.strategy.UUIDv1"
    )
    public UUID id;
}
