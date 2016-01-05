package com.yp.enstudy.utils;

import java.security.MessageDigest;

public final class MD5 {
    public static final String getMessageDigest(byte[] paramArrayOfByte) {
        char[] arrayOfChar1 = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest localMessageDigest;
            (localMessageDigest = MessageDigest.getInstance("MD5"))
                    .update(paramArrayOfByte);
            int i;
            char[] arrayOfChar2 = new char[(i = (paramArrayOfByte = localMessageDigest
                    .digest()).length) * 2];
            int j = 0;
            for (int k = 0; k < i; ++k) {
                int l = paramArrayOfByte[k];
                arrayOfChar2[(j++)] = arrayOfChar1[(l >>> 4 & 0xF)];
                arrayOfChar2[(j++)] = arrayOfChar1[(l & 0xF)];
            }
            return new String(arrayOfChar2);
        } catch (Exception localException) {
        }
        return null;
    }

    public static final byte[] getRawDigest(byte[] paramArrayOfByte) {
        try {
            MessageDigest localMessageDigest;
            (localMessageDigest = MessageDigest.getInstance("MD5"))
                    .update(paramArrayOfByte);
            return localMessageDigest.digest();
        } catch (Exception localException) {
        }
        return null;
    }
}