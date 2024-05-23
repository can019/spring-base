package com.example.base.global.util.generator;

import com.fasterxml.uuid.Generators;

import java.nio.ByteBuffer;

public class UUID {
    /**
     *
     * thread-safe
     * @return sequential uuid based on UUID v1
     */
    public static java.util.UUID generateSequentialUUIDV1(){
        java.util.UUID newUuid = Generators.timeBasedGenerator().generate();
        String[] uuidArr = newUuid.toString().split("-");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(uuidArr[2]).append('-').append(uuidArr[1]).append('-').append(uuidArr[0])
                .append('-').append(uuidArr[3]).append('-').append(uuidArr[4]);
        return java.util.UUID.fromString(stringBuffer.toString());
    }
    /**
     *
     * thread-safe
     * @return sequential uuid based on UUID v1 without hyphen
     */
    public static String generateSequentialUUIDV1WithoutHyphen(){
        java.util.UUID newUuid = Generators.timeBasedGenerator().generate();
        String[] uuidArr = newUuid.toString().split("-");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(uuidArr[2]).append(uuidArr[1]).append(uuidArr[0]).append(uuidArr[3]).append(uuidArr[4]);
        return stringBuffer.toString();
    }

    public static byte[] generateSequentialUUIDV1WithoutHyphenAsByte(){
        String uuidWithoutHyphen = generateSequentialUUIDV1WithoutHyphen();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(Long.parseUnsignedLong(uuidWithoutHyphen.substring(0, 16), 16));
        bb.putLong(Long.parseUnsignedLong(uuidWithoutHyphen.substring(16), 16));
        System.out.println(uuidWithoutHyphen);
        System.out.println(bb.array());
        return bb.array();
    }
}
