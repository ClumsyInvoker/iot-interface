package com.lanyun.iot.gateway.controller.mqtt.conf;

/**
 * MQTT客户端配置接口
 */
public interface MqttClientConfig {

    String getServerURI();
    //
    String getClientId();
    String getUserName();
    String getPassWord();
    //逗号分隔
    String[] getTopicArray();
    int[] getQosArray();
    //线程数
    int getThreadNum();

    //
    int getConnectionTimeout();

    int getKeepAliveInterval();

    String getRandomClientId();
}
