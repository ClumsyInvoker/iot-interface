package com.lanyun.iot.gateway.controller.netty.codec.json;

import com.alibaba.fastjson.JSON;
import com.lanyun.iot.gateway.model.enums.MessageVersionEnum;
import com.lanyun.iot.gateway.controller.mqtt.cmd.AutoRepairCmdEnum;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceCmdEnum;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import com.lanyun.iot.gateway.controller.mqtt.cmd.Version_4_CmdEnum;
import com.lanyun.iot.gateway.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * JSON格式消息解码
 * 除设备认证、设备升级以外的设备报文解析
 */
@Slf4j
public class JsonMessageDecoder implements MessageDecoder<DeviceMessage> {

    @Override
    public DeviceMessage decode(byte[] payload) {
        String jsonStr = payloadToString(payload);
        log.warn("收到的设备的报文：{}",jsonStr);
        DeviceMessage deviceMessage = JSON.parseObject(jsonStr, DeviceMessage.class);
        if (deviceMessage == null) {
            log.error("消息解析失败 : " + jsonStr);
            return null;
        }
        notNull(deviceMessage.getCmd());
        //根据命令id确定数据格式
        Class<?> dataClass = null;
        MessageVersionEnum versionEnum = MessageVersionEnum.getItem(deviceMessage.getVersion());
        if (versionEnum==null){
            log.info("version:{} 的cmd枚举类还未定义",deviceMessage.getVersion());
            return null;
        }

        switch (versionEnum){
            case MESSAGE_VERSION_ENUM_1:
                dataClass = DeviceCmdEnum.getCmdClass(deviceMessage.getCmd());
                break;
            case MESSAGE_VERSION_ENUM_3:
                dataClass = AutoRepairCmdEnum.getCmdClass(deviceMessage.getCmd());
                break;
            case MESSAGE_VERSION_ENUM_4:
                // 待处理
                dataClass = Version_4_CmdEnum.getCmdClass(deviceMessage.getCmd());
                break;
        }

        if (dataClass != null) {
            Object data = JsonUtil.convert(deviceMessage.getData(), dataClass);
            deviceMessage.setData(data);
        }
        return deviceMessage;
    }
}
