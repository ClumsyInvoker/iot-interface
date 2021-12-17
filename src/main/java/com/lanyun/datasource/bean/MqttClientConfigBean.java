package com.lanyun.datasource.bean;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-27 14:29
 */
@Configuration
@Data
public class MqttClientConfigBean {

    @Value("${mqtt.server.url}")
    private String serverUrl;

    @Value("${mqtt.client.id}")
    private String clientId;

    @Value("${mqtt.client.random}")
    private Boolean clientRandom;

    @Value("${mqtt.userName}")
    private String userName;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.mock.clientId}")
    private String mockClientId;

    @Value("${mqtt.mock.enable}")
    private Boolean enable;

    @Value("${mqtt.attentionTopics}")
    private String attentionTopics;

    @Value("${mqtt.qos}")
    private String qos;

    @Value("${mqtt.threadNum}")
    private Integer threadNum;

    @Value("${mqtt.connectionTimeout}")
    private Integer connectionTimeout;

    @Value("${mqtt.keepAliveInterval}")
    private Integer keepAliveInterval;

}
