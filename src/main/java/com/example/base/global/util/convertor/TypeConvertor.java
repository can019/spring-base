package com.example.base.global.util.convertor;

public class TypeConvertor {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String byteArrayToHexString(byte[] bytes){
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }
}
