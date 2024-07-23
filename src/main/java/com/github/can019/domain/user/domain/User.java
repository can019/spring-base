package com.github.can019.domain.user.domain;

import com.github.can019.global.entity.BaseTimeEntity;
import com.github.can019.global.util.convertor.TypeConvertor;
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
            strategy = "com.github.can019.domain.user.domain.UUIDV1IdGenerator"
    )
    private byte[] id;

    public byte[] getId() {
        return this.id;
    }

    public String getIdAsString() {
        return TypeConvertor.byteArrayToHexString(getId());
    }
}
