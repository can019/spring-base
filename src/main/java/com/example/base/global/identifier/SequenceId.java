package com.example.base.global.identifier;

import com.example.base.global.util.convertor.TypeConvertor;
import com.example.base.global.util.generator.UUID;

import java.util.Arrays;
import java.util.Objects;

public class SequenceId implements Identifier{
    private final byte[] id;
    private String strId;

    public SequenceId(byte[] id){
        this.id = id;
    }

    public SequenceId(){
        this(UUID.generateSequentialUUIDV1WithoutHyphenAsByte());
    }

    public SequenceId(String id){
        this.strId = id;
        this.id = TypeConvertor.hexStringToByte(id);
    }

    private byte[] getId(){
        return this.id;
    }

    @Override
    public String getIdAsString() {
        if(strId == null){
            strId = TypeConvertor.byteArrayToHexString(this.getId());
        }
        return strId;
    }

    @Override
    public byte[] getIdAsByte() {
        return this.getId();
    }

    @Override
    public boolean equals(Object target) {
        if (this == target)
            return true;
        if (!(target instanceof SequenceId))
            return false;
        SequenceId targetSequenceId = (SequenceId)target;
        return Arrays.equals(this.id, targetSequenceId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
