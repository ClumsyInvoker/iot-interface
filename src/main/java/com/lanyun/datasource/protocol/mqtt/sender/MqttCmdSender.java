package com.lanyun.datasource.protocol.mqtt.sender;

import com.alibaba.fastjson.JSON;
import com.lanyun.datasource.entity.MqttSendMessage;
import com.lanyun.datasource.protocol.handler.cmd.CmdSender;
import com.lanyun.datasource.protocol.mqtt.MqttMessageSender;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import com.lanyun.datasource.service.MqttSendMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MQTT消息下发器
 */
@Slf4j
@Component
public class MqttCmdSender implements CmdSender {

    @Autowired
    private MqttMessageSender mqttMessageSender;

    @Autowired
    private MqttSendMessageService mqttSendMessageService;

    @Override
    public Integer send(String deviceTopicPrefix, String serialNo, DeviceMessage cmd) {
        //每个设备有单独的topic
        mqttMessageSender.sendMessage(deviceTopicPrefix.concat(serialNo), cmd);
        sendLog(cmd);
        return SUCCESS;
    }

    private void sendLog(DeviceMessage deviceMessage) {
        MqttSendMessage message = new MqttSendMessage();
        message.setVersion(deviceMessage.getVersion());
        message.setMsgNo(deviceMessage.getMsgNo());
        message.setMachNo(deviceMessage.getMachNo());
        message.setCmd(deviceMessage.getCmd());
        message.setTime(deviceMessage.getTime());
        message.setData(JSON.toJSONString(deviceMessage.getData()));
        message.setCreateTime(System.currentTimeMillis());
        try {
            mqttSendMessageService.insert(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("下行日志：" + JSON.toJSONString(deviceMessage.getData()), e);
        }
    }

}
