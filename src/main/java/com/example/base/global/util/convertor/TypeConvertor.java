package com.example.base.global.util.convertor;

public class TypeConvertor {

    public static String byteArrayToHexString(byte[] bytes){
        StringBuilder builder = new StringBuilder();

        for (byte data : bytes) {
            builder.append(String.format("%02X ", data));
        }

        return builder.toString();
    }
}
