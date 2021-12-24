package com.lanyun.iot.gateway.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 22:51 2018/9/6
 * @ Description：${description}
 * @ Modified By：
 */
public class BinaryUtil {

    public static byte[] subArray(byte[] data, int from, int to) {
        return copy(data, from, to);
    }

    public static short getShort(byte[] data, int from) {
        if (from < 0 || from > data.length - 2)
            throw new IllegalArgumentException("error start index " + from + " for data length " + data.length);
        return (short) ((data[from] << 8) + data[from + 1]);
    }

    public static boolean equals(byte[] arr1, byte[] arr2) {
        return Arrays.equals(arr1, arr2);
    }

    public static byte[] getBytesRequiredLength(String content, int length) {
        if (content == null)
            throw new NullPointerException("content is null");
        if (length <= 0 || length > 1024)
            throw new IllegalArgumentException("illegal length param " + length);
        //
        byte[] data = content.getBytes();
        if (data.length != content.length())
            throw new IllegalArgumentException("content contains no ASCII char: [" + content + "]");
        //
        if (data.length != length)
            throw new IllegalArgumentException("illegal string length for string [" + content + "], requires " + length);
        //
        return data;
    }
    /**
     * 转换为short
     *
     * @param data
     * @return
     */
    public static short toShort(byte[] data) {
        if (data == null)
            throw new NullPointerException("data is null");
        if (data.length != 2)
            throw new IllegalArgumentException("data length is must equals 2, actually is " + data.length);
        short s0 = (short) (data[0] & 0xff);
        short s1 = (short) (data[1] & 0xff);
        s0 <<= 8;
        return  (short) (s0 | s1);
    }

    public static byte[] copy(byte[] arr) {
        if (arr == null)
            return null;
        return Arrays.copyOf(arr, arr.length);
    }

    /**
     * copy数组中的一部分，包含开头索引，不包含结尾索引
     *
     * @param arr
     * @param from
     * @param to
     * @return
     */
    public static byte[] copy(byte[] arr, int from, int to) {
        if (arr == null)
            return null;
        return Arrays.copyOfRange(arr, from, to);
    }

    public static String toHexString(byte a) {
        return Integer.toHexString(a);
    }

    public static String toHexString(byte[] a) {
        if (a == null)
            return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        String s;
        for (int i = 0; ; i++) {
            s = Integer.toHexString(a[i] & 0xFF);
            if (s.length() == 1)
                b.append("0");
            b.append(s);
            if (i == iMax)
                return b.append(']').toString();
            b.append(" ");
        }
    }

    /**
     * 校验CRC
     *
     * @param data
     * @param crc16
     */
    public static boolean checkCrc(byte[] data, byte[] crc16) {
        if (data == null || data.length < 1)
            throw new IllegalArgumentException("数据不正确：" + Arrays.toString(data));
        if (crc16 == null || crc16.length != 2)
            throw new IllegalArgumentException("CRC16码不正确：" + Arrays.toString(crc16));
        byte[] crc = calcCRC16(data);
        return Arrays.equals(crc, crc16);
    }

    /**
     * CRC16校验
     */
    public static byte[] calcCRC16(byte[] pArray) {
        return calcCRC16(pArray, pArray.length);
    }

    /**
     * CRC16校验
     */
    public static byte[] calcCRC16(byte[] pArray, int length) {
        int wCRC = 0xFFFF;
        length = length > pArray.length ? pArray.length : length;
        int CRC_Count = length;
        int num = 0;
        while (CRC_Count > 0) {
            CRC_Count--;
            wCRC = wCRC ^ (0xFF & pArray[num++]);
            for (int i = 0; i < 8; i++) {
                if ((wCRC & 0x0001) == 1) {
                    wCRC = wCRC >> 1 ^ 0xA001;
                } else {
                    wCRC = wCRC >> 1;
                }
            }
        }
//        return new byte[]{
//                (byte) ((wCRC >> 8) & 0xFF),
//                (byte) (wCRC & 0xFF)
//        };
        return new byte[]{
                (byte) (wCRC & 0xFF),
                (byte) ((wCRC >> 8) & 0xFF)
        };

    }


    /**
     * 异或
     */
    public static byte xor(byte[] data, int length) {
        length = length > data.length ? data.length : length;
        byte xor = 0x00;
        for (int i = 0; i < length; ++i) {
            xor ^= data[i];
        }
        return xor;
    }

    /**
     * 异或
     */
    public static byte xor(byte[] data) {
        return xor(data, data.length);
    }

    /**
     * 1.1设备升级用，后续废弃
     * @20200430
     * 二进制的序列号转换为字符串
     *
     * @param serialNoArray
     * @return
     */
    public static String parseOldBinarySerialNo(byte[] serialNoArray) {
        if (serialNoArray == null || serialNoArray.length != 7)
            throw new IllegalArgumentException("error serialNoArray: " + Arrays.toString(serialNoArray));
        //
        if ('U' != serialNoArray[0])
            throw new IllegalArgumentException("serialNoArray is not start with 'U', param is " + Arrays.toString(serialNoArray));
        //
        StringBuilder sb = new StringBuilder("U");
        for (int i = 1; i < serialNoArray.length; ++i) {
            String s = Integer.toString(serialNoArray[i]);
            if (s.length() == 1){
                sb.append("0");
            }
            sb.append(s);
        }
        //
        return sb.toString();
    }

    /**
     * V2.1，以后用这个版本
     * @20200430
     * 二进制的序列号转换为字符串
     *
     * @param serialNoArray
     * @return
     */
    public static String parseNewBinarySerialNo(byte[] serialNoArray) {
        if (serialNoArray == null || serialNoArray.length != 7)
            throw new IllegalArgumentException("error serialNoArray: " + Arrays.toString(serialNoArray));
        //
        if ('U' != serialNoArray[0])
            throw new IllegalArgumentException("serialNoArray is not start with 'U', param is " + Arrays.toString(serialNoArray));
        //
        StringBuilder sb = new StringBuilder("U");
        for (int i = 1; i < serialNoArray.length; ++i) {
            String s = Integer.toHexString(serialNoArray[i] & 0xFF);
            if (s.length() == 1){
                sb.append("0");
            }
            sb.append(s);
        }
        //
        return sb.toString();
    }

    /**
     * 序列号转换为二进制
     *
     * @param serialNo
     * @return
     */
    public static byte[] serialNoToByteArray(String serialNo) {
        if (StringUtils.isBlank(serialNo) || serialNo.length() != 13 || !serialNo.startsWith("U"))
            throw new IllegalArgumentException("error serialNo format: " + serialNo);
        //
        byte[] arr = new byte[7];
        //
        arr[0] = 'U';
        //
        String subSerialNo = serialNo.substring(1);
        for (int i = 0; i < 6; ++i) {
            String str = subSerialNo.substring(i * 2, i * 2 + 2);
            int dat = Integer.parseInt(str);
            if (dat < 0 || dat > 99)
                throw new IllegalStateException("解析序列号异常，str = " + str);
            
            arr[i + 1] = (byte) (dat & 0xFF);
        }
        return arr;
    }

    //用于测试
    public static void main(String[] args) {
        String serialNo = "U010519060159";
        byte[] arr = serialNoToByteArray(serialNo);
        System.out.println(arr);
        String parsed = parseNewBinarySerialNo(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(parsed);
        String srt = Integer.toHexString(0x01);
        System.out.println(srt);
        //
        byte a1 = 4;
        int a = (a1 << 8) + 96;
        System.out.println(a);
    }
}
