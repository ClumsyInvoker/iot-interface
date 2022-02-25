package com.lanyun.iot.gateway.controller.netty.codec.json;

import com.alibaba.fastjson.JSON;
import com.lanyun.iot.gateway.model.enums.MessageVersionEnum;
import com.lanyun.iot.gateway.controller.mqtt.cmd.AutoRepairCmdEnum;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceCmdEnum;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import com.lanyun.iot.gateway.controller.mqtt.cmd.IotMessage;
import com.lanyun.iot.gateway.controller.mqtt.cmd.Version_4_CmdEnum;
import com.lanyun.iot.gateway.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * JSON格式消息解码
 * 除设备认证、设备升级以外的设备报文解析
 */
@Slf4j
public class NewJsonMessageDecoder implements MessageDecoder<IotMessage> {

    @Override
    public IotMessage decode(byte[] payload) {
        String jsonStr = payloadToString(payload);
        log.warn("收到的设备的报文：{}",jsonStr);
        IotMessage iotMessage = JSON.parseObject(jsonStr, IotMessage.class);
        if (iotMessage == null) {
            log.error("消息解析失败 : " + jsonStr);
            return null;
        }

        return iotMessage;
    }
}
