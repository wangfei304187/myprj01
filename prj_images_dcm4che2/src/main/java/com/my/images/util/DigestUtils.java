package com.my.images.util;

import java.security.MessageDigest;

public class DigestUtils {
    private static String byteArrayToHexString(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < bytes.length; i++)
        {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1)
            {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String md5(String origin)
    {
        String resultString = null;
        try
        {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return resultString;
    }
}
