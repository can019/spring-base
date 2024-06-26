package com.example.base.domain.user.domain;

import com.example.base.global.entity.BaseTimeEntity;
import com.example.base.global.identifier.SequenceId;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import static com.example.base.global.util.convertor.TypeConvertor.byteArrayToHexString;

@Entity
@Table(name="USER")
@Slf4j
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @Column(name="ID",columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "SequenceId")
    @GenericGenerator(
            name="SequenceId",
            strategy = "com.example.base.domain.user.domain.SequenceIdStrategy"
    )
    private byte[] id;

    @Transient
    private SequenceId identifier;


    /**
     * @deprecated Use getIdentifier()
     * @return
     */
    public byte[] getId() {
        return this.id;
    }

    /**
     * @deprecated Use getIdentifier()
     * @return
     */
    public String getIdAsString() {
        return byteArrayToHexString(getId());
    }

    @Nullable
    public SequenceId getIdentifier() {
        if(this.id != null && this.identifier == null){
            this.identifier = new SequenceId(this.id);
        }
        return this.identifier;
    }

    public User(SequenceId identifier) {
        this.identifier = identifier;
        this.id = identifier.getIdAsByte();
    }
}
