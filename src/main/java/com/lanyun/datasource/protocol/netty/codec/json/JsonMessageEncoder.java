package com.lanyun.datasource.protocol.netty.codec.json;

import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import com.lanyun.datasource.utils.JsonUtil;

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
