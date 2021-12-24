package com.lanyun.iot.gateway.utils;

import java.text.DecimalFormat;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 23:20 2018/6/26
 * @ Description：${description}
 * @ Modified By：
 */
public class NumberUtil {

    public static String numberToFormat(Long num, String format)
    {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(num);
    }

    public static String numberToFormat(Integer num, String format)
    {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(num);
    }

    public static Integer parseInt(String num)
    {
        return Integer.parseInt(num);
    }
}
