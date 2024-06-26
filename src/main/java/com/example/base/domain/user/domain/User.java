package com.example.base.domain.user.domain;

import com.example.base.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import static com.example.base.global.util.convertor.TypeConvertor.byteArrayToHexString;

@Entity
@Table(name="USER")
@Slf4j
public class User extends BaseTimeEntity {
    @Id
    @Column(name="ID",columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "SequenceId")
    @GenericGenerator(
            name="SequenceId",
            strategy = "com.example.base.domain.user.domain.SequenceIdStrategy"
    )
    private byte[] id;

    public byte[] getId() {
        return this.id;
    }

    public String getIdAsString() {
        return byteArrayToHexString(getId());
    }
}
