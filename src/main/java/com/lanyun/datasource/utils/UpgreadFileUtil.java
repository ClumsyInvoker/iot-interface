package com.lanyun.datasource.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 23:57 2018/9/27
 * @ Description：${description}
 * @ Modified By：
 */
@Slf4j
public class UpgreadFileUtil {

    public static final int SPLITTER_LEN = 252;

    /**
     * 获取分片内容
     *
     * @param all
     * @param splitterNum
     * @param splitterLen
     * @return
     */
    public static byte[] getSplitter(byte[] all, int splitterNum, int splitterLen) {
        if (all == null)
            return null;
        //from
        int from = splitterNum * splitterLen;
        if (from >= all.length)
            throw new IllegalArgumentException(String.format("splitterNum is out of bound, len=%s, splitterNum=%s, splitterLen=%s", all.length, splitterNum, splitterLen));
        //to
        int to = from + splitterLen;
        if (to > all.length)
            to = all.length;
        if (from > to)
            throw new IllegalArgumentException(String.format("from %s is great than to %s", from, to));
        //
        return BinaryUtil.copy(all, from, to);
    }

    /**
     * 填充特定字符
     * @param src
     * @param len
     * @param extendContent
     * @return
     */
    public static byte[] extend(byte[] src, int len, byte extendContent)
    {
        if (src == null)
            return null;
        if (src.length >= len)
            return src;
        //
        byte[] target = Arrays.copyOf(src, len);
        for (int i=src.length; i<len; ++i)
            target[i] = extendContent;
        //
        return target;
    }

    public static byte[] extendFF(byte[] src, int len)
    {
        return extend(src, len, (byte)0xFF);
    }

    public static byte[] extendFF(byte[] src)
    {
        return extend(src, SPLITTER_LEN, (byte)0xFF);
    }
    /**
     * 获取分片内容
     *
     * @param all
     * @param splitterNum
     * @return
     */
    public static byte[] getSplitter(byte[] all, int splitterNum) {
        return getSplitter(all, splitterNum, SPLITTER_LEN);
    }

    /**
     * 获取分片数
     * @param file
     * @param splitterLen
     * @return
     */
    public static short getSplitterNum(byte[] file, int splitterLen) {
        if (file == null)
            return 0;
        if (file.length <= splitterLen)
            return 1;
        int splitterNum = file.length / splitterLen + (file.length % splitterLen == 0 ? 0 : 1);
        if (splitterNum > Short.MAX_VALUE)
            throw new IllegalStateException("file splitter is great than Short.Max, that is " + splitterNum);
        return (short) splitterNum;
    }

    /**
     * 获取分片数
     * @param file
     * @return
     */
    public static short getSplitterNum(byte[] file) {
        return getSplitterNum(file, SPLITTER_LEN);
    }
}
