package com.github.can019.base.core.util.convertor;

import java.nio.ByteBuffer;

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

    public static byte[] hexStringToByte(String str) throws NumberFormatException{
        if(str.length() < 16) throw new StringIndexOutOfBoundsException("Input string length should over 16");
        if(str.length() > 32) throw new StringIndexOutOfBoundsException("Input string length should under 32");

        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(Long.parseUnsignedLong(str.substring(0, 16), 16));
        bb.putLong(Long.parseUnsignedLong(str.substring(16),16));
        return bb.array();
    }
}
