package com.lanyun.datasource.bean;

import com.lanyun.datasource.protocol.mqtt.conf.DefaultMqttClientConfig;
import com.lanyun.datasource.protocol.mqtt.conf.MqttClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-27 14:26
 */
@Component
@Order(1)
public class InitMqttClientBean {

    @Autowired
    private MqttClientConfigBean mqttConfig;

    @Bean
    public MqttClientConfig mqttClientConfig() {
        DefaultMqttClientConfig config = new DefaultMqttClientConfig();
        config.setServerURI(mqttConfig.getServerUrl());
        config.setClientId(mqttConfig.getClientId());
        config.setEnableRandomClientId(mqttConfig.getClientRandom());
        config.setUserName(mqttConfig.getUserName());
        config.setPassWord(mqttConfig.getPassword());
        config.setAttentionTopics(mqttConfig.getAttentionTopics());
        config.setQos(mqttConfig.getQos());
        config.setThreadNum(mqttConfig.getThreadNum());
        config.setConnectionTimeout(mqttConfig.getConnectionTimeout());
        config.setKeepAliveInterval(mqttConfig.getKeepAliveInterval());
        return config;
    }
}
