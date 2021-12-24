package com.lanyun.iot.gateway.controller.netty.codec.json;

import com.lanyun.iot.gateway.controller.mqtt.cmd.IDeviceMessage;

/**
 * DeviceMessage编码为字节码
 */
public interface MessageEncoder<MSG extends IDeviceMessage> extends MessageCodec<MSG> {

    byte[] encode(MSG message);
}
