package com.lanyun.datasource.protocol.netty.codec.json;

import com.lanyun.datasource.protocol.cmd.IDeviceMessage;
import com.lanyun.datasource.utils.CodecUtil;

/**
 * 字符串消息编码/解码接口
 */
public interface MessageCodec<MSG extends IDeviceMessage> {


    default String payloadToString(byte[] payload)
    {
        return CodecUtil.bytesToString(payload);
    }

    default byte[] stringToPayload(String str)
    {
       return CodecUtil.stringToBytes(str);
    }
}
