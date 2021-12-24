package com.lanyun.iot.gateway.controller.netty.codec.json;

import com.lanyun.iot.gateway.controller.mqtt.cmd.IDeviceMessage;

/**
 * 字节码解析为DeviceMessage接口
 */
public interface MessageDecoder<MSG extends IDeviceMessage> extends MessageCodec<MSG> {
    MSG decode(byte[] payload);
}
