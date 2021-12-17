package com.lanyun.datasource.protocol.mqtt;

import com.lanyun.datasource.protocol.mqtt.client.IrbMqttClient;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import com.lanyun.datasource.protocol.netty.codec.json.JsonMessageEncoder;
import com.lanyun.datasource.protocol.netty.codec.json.MessageEncoder;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * MQTT消息发送类
 */
@Slf4j
@Component
public class MqttMessageSender {
    private static final int DEFAULT_QOS = 1;

    @Resource
    private IrbMqttClient client;

    private MessageEncoder<DeviceMessage> encoder = new JsonMessageEncoder();

    public IMqttDeliveryToken sendMessage(String topic, DeviceMessage message, int qos) {
        byte[] payload = encoder.encode(message);
        MqttMessage mqttMessage = new MqttMessage(payload);
        mqttMessage.setQos(qos);
        return client.sendMessage(topic, mqttMessage);
    }

    public IMqttDeliveryToken sendMessage(String topic, DeviceMessage message) {
        return sendMessage(topic, message, DEFAULT_QOS);
    }

}
