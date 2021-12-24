package com.lanyun.iot.gateway.controller.netty.codec.json;

import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import com.lanyun.iot.gateway.utils.JsonUtil;

/**
 * JSON格式报文转为字节码
 */
public class JsonMessageEncoder implements MessageEncoder<DeviceMessage> {


    @Override
    public byte[] encode(DeviceMessage message) {
        String jsonStr = JsonUtil.toJson(message);
        return stringToPayload(jsonStr);
    }
}
