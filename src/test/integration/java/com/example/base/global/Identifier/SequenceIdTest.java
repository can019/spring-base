package com.example.base.global.Identifier;

import com.example.base.global.identifier.SequenceId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SequenceIdTest {
    @Test
    @DisplayName("기본 생성자로 생성한 경우 getIdAsString()은 Null이면 안된다.")
    void noArgsConstructorGetIdAsStringNotNull(){
        SequenceId sequenceId = new SequenceId();

        assertThat(sequenceId.getIdAsString()).isNotNull();
    }

    @Test
    @DisplayName("기본 생성자로 생성한 경우 getIdAsByte()은 Null이면 안된다.")
    void noArgsConstructorGetIdAsByteNotNull(){
        byte[] id = new SequenceId().getIdAsByte();
        SequenceId sequenceId = new SequenceId(id);

        assertThat(sequenceId.getIdAsByte()).isNotNull();
    }

    @Test
    @DisplayName("SequenceId(byte[] id)로 생성한 경우 getIdAsString()은 Null이면 안된다.")
    void byteArrayConstructorGetIdAsStringNotNull(){
        byte[] id = new SequenceId().getIdAsByte();
        SequenceId sequenceId = new SequenceId(id);

        assertThat(sequenceId.getIdAsString()).isNotNull();
    }

    @Test
    @DisplayName("SequenceId(byte[] id)로 생성한 경우 getIdAsByte()은 Null이면 안된다.")
    void byteArrayConstructorGetIdAsByteNotNull(){
        byte[] id = new SequenceId().getIdAsByte();
        SequenceId sequenceId = new SequenceId(id);

        assertThat(sequenceId.getIdAsByte()).isNotNull();
    }


    @Test
    @DisplayName("SequenceId(String id)로 생성한 경우 getIdAsString()은 Null이면 안된다.")
    void stringConstructorGetIdAsStringNotNull(){
        String id = new SequenceId().getIdAsString();
        SequenceId sequenceId = new SequenceId(id);

        assertThat(sequenceId.getIdAsString()).isNotNull();
    }

    @Test
    @DisplayName("SequenceId(String id)로 생성한 경우 getIdAsByte()은 Null이면 안된다.")
    void stringConstructorGetIdAsByteNotNull(){
        String id = new SequenceId().getIdAsString();
        SequenceId sequenceId = new SequenceId(id);

        assertThat(sequenceId.getIdAsByte()).isNotNull();
    }

    @Test
    @DisplayName("SequenceId의 값이 같은 경우 equals 연산에서 같아야 한다.")
    void equalWhenByteIsEqual(){
        SequenceId sequenceId1 = new SequenceId();
        SequenceId sequenceId2 = new SequenceId(sequenceId1.getIdAsByte());
        SequenceId sequenceId3 = new SequenceId(sequenceId1.getIdAsString());
        Assertions.assertAll(
                ()->assertThat(sequenceId1.equals(sequenceId2)).isTrue(),
                ()->assertThat(sequenceId2.equals(sequenceId3)).isTrue(),
                ()->assertThat(sequenceId3.equals(sequenceId1)).isTrue()
        );
    }

}
