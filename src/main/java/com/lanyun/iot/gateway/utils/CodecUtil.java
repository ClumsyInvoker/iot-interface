package com.lanyun.iot.gateway.utils;

import java.io.UnsupportedEncodingException;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 23:15 2018/7/11
 * @ Description：${description}
 * @ Modified By：
 */
public class CodecUtil {

    public static final String UTF_8 = "UTF-8";

    public static String bytesToString(byte[] payload)
    {
        try {
            return new String(payload, UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncoding " + UTF_8, e);
        }
    }

    public static byte[] stringToBytes(String str)
    {
        try {
            return str.getBytes(UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncoding " + UTF_8, e);
        }
    }
}
