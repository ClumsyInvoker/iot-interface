//package com.lanyun.datasource;
//
//import com.alibaba.fastjson.JSON;
//
//import javax.crypto.*;
//import java.nio.charset.Charset;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//
//public class DemoMain {
//    public static void main(String[] args) {
//        System.out.println(hexStrToStr("34"));
//
//    }
//
//    public static String hexStrToStr(String hexStr) {
//        String str = "0123456789ABCDEF";
//        char[] hexs = hexStr.toCharArray();
//        byte[] bytes = new byte[hexStr.length() / 2];
//        int n;
//        for (int i = 0; i < bytes.length; i++) {
//            n = str.indexOf(hexs[2 * i]) * 16;
//            n += str.indexOf(hexs[2 * i + 1]);
//            bytes[i] = (byte) (n & 0xff);
//        }
//        return new String(bytes);
//    }
//}
