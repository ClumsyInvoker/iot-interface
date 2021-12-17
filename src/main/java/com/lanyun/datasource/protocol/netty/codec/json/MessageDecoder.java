package com.lanyun.datasource.protocol.netty.codec.json;

import com.lanyun.datasource.protocol.cmd.IDeviceMessage;

/**
 * 字节码解析为DeviceMessage接口
 */
public interface MessageDecoder<MSG extends IDeviceMessage> extends MessageCodec<MSG> {
    MSG decode(byte[] payload);
}
