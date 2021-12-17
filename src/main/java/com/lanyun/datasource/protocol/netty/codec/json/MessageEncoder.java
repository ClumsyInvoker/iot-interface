package com.lanyun.datasource.protocol.netty.codec.json;

import com.lanyun.datasource.protocol.cmd.IDeviceMessage;

/**
 * DeviceMessage编码为字节码
 */
public interface MessageEncoder<MSG extends IDeviceMessage> extends MessageCodec<MSG> {

    byte[] encode(MSG message);
}
