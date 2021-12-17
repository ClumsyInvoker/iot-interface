package com.lanyun.datasource.protocol.mqtt.handler;

import com.lanyun.datasource.protocol.mqtt.client.IrbMqttClient;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * MQTT消息监听器
 */
@Slf4j
@Component
public class DeviceMessageListener implements MqttCallbackExtended {

    @Resource
    private DeviceMessageDispatcher dispatcher;   //发送消息

    private IrbMqttClient client;

    public DeviceMessageListener(DeviceMessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("MQTT 连接成功，MQTT-Server 地址：" + serverURI);
        client.subscribeTopics();
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.warn("MQTT 连接断开", cause);
        //已经设置了自动连接属性为true，这里不需要了
//        client.reconnect();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("收到 Topic 为："+topic+"的 mqtt 消息，ID = "+message.getId()+", Qos is " + message.getQos());
        }
        // 把设备上报的消息分发给指定的处理器
        dispatcher.dispatchMessage(topic, message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //记录日志
        if (log.isDebugEnabled()) {
            log.debug("MQTT 消息投递成功，Topic = "+Arrays.toString(token.getTopics()));
        }
    }

    public IrbMqttClient getClient() {
        return client;
    }

    public void setClient(IrbMqttClient client) {
        this.client = client;
    }
}
