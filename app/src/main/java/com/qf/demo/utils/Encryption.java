package com.qf.demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/11/3.
 */
public class Encryption {
    public static String md5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            String rel = toHexString(messageDigest.digest());
            return rel;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String toHexString(byte[] bytes) {
        if (bytes == null)
            return null;
        StringBuffer stringBuffer = new StringBuffer(2 * bytes.length);
        for (int i = 0; ; i++) {
            if (i >= bytes.length) {
                return stringBuffer.toString();
            }
            String str = Integer.toString(0xFF & bytes[i], 16);
            if (str.length() == 1) {
                str = "0" + str;
            }
            stringBuffer.append(str);
        }
    }
}
