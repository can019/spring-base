package com.example.base.domain.user.domain;

import com.example.base.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="USER")
@Slf4j
public class User extends BaseTimeEntity {
    @Id
    @Column(name="ID",columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "UUID_V1_generator_without_hyphen")
    @GenericGenerator(
            name="UUID_V1_generator_without_hyphen",
            strategy = "com.example.base.domain.user.domain.UUIDV1IdGenerator"
    )
    private byte[] id;

    public byte[] getId() {
        return this.id;
    }
}
