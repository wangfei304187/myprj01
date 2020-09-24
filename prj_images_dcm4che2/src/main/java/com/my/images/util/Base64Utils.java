package com.my.images.util;

import java.nio.charset.Charset;

public class Base64Utils {

    protected static final Charset UTF8 = Charset.forName("UTF-8");
    protected static final Charset US_ASCII = Charset.forName("US-ASCII");

    public static String encode(byte[] data) {
        return javax.xml.bind.DatatypeConverter.printBase64Binary(data);
    }

    public static byte[] decode(String encoded) {
        return javax.xml.bind.DatatypeConverter.parseBase64Binary(encoded);
    }

    // ref: io.jsonwebtoken.impl.Base64UrlCodec
    public String encodeUrl(byte[] data) {
        String base64Text = encode(data);
        byte[] bytes = base64Text.getBytes(US_ASCII);

        // base64url encoding doesn't use padding chars:
        bytes = removePadding(bytes);

        // replace URL-unfriendly Base64 chars to url-friendly ones:
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == '+') {
                bytes[i] = '-';
            } else if (bytes[i] == '/') {
                bytes[i] = '_';
            }
        }

        return new String(bytes, US_ASCII);
    }

    protected byte[] removePadding(byte[] bytes) {

        byte[] result = bytes;

        int paddingCount = 0;
        for (int i = bytes.length - 1; i > 0; i--) {
            if (bytes[i] == '=') {
                paddingCount++;
            } else {
                break;
            }
        }
        if (paddingCount > 0) {
            result = new byte[bytes.length - paddingCount];
            System.arraycopy(bytes, 0, result, 0, bytes.length - paddingCount);
        }

        return result;
    }

    public byte[] decodeUrl(String encoded) {
        char[] chars = encoded.toCharArray(); // always ASCII - one char == 1 byte

        // Base64 requires padding to be in place before decoding, so add it if
        // necessary:
        chars = ensurePadding(chars);

        // Replace url-friendly chars back to normal Base64 chars:
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '-') {
                chars[i] = '+';
            } else if (chars[i] == '_') {
                chars[i] = '/';
            }
        }

        String base64Text = new String(chars);

        return decode(base64Text);
    }

    protected char[] ensurePadding(char[] chars) {

        char[] result = chars; // assume argument in case no padding is necessary

        int paddingCount = 0;

        // fix for https://github.com/jwtk/jjwt/issues/31
        int remainder = chars.length % 4;
        if (remainder == 2 || remainder == 3) {
            paddingCount = 4 - remainder;
        }

        if (paddingCount > 0) {
            result = new char[chars.length + paddingCount];
            System.arraycopy(chars, 0, result, 0, chars.length);
            for (int i = 0; i < paddingCount; i++) {
                result[chars.length + i] = '=';
            }
        }

        return result;
    }
}
