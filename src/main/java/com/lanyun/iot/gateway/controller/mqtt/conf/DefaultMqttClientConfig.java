package com.lanyun.iot.gateway.controller.mqtt.conf;

import com.google.common.base.Splitter;
import com.lanyun.iot.gateway.utils.NumberUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;

/**
 * 默认MQTT客户端配置
 */
@Data
public class DefaultMqttClientConfig implements MqttClientConfig {

    private static final String SPLITTER = ",";

    private String ServerURI;
    private String clientId;
    private Boolean enableRandomClientId = Boolean.FALSE;
    private String userName;
    private String passWord;
    //
    private String attentionTopics;
    private String qos;
    //
    private int threadNum;
    private int connectionTimeout;
    private int keepAliveInterval;

    @Override
    public String[] getTopicArray() {
        List<String> ret = Splitter.on(SPLITTER).splitToList(attentionTopics);
        return ret.toArray(new String[ret.size()]);
    }

    @Override
    public int[] getQosArray() {
        List<String> ret = Splitter.on(SPLITTER).splitToList(qos);
        int[] qos = new int[ret.size()];
        for (int i = 0; i < ret.size(); ++i) {
            qos[i] = NumberUtil.parseInt(ret.get(i));
        }
        return qos;
    }

    @Override
    public String getRandomClientId() {
        if (!enableRandomClientId && StringUtils.isBlank(clientId)) {
            throw new IllegalStateException("nether enable random clientId nor has a faxed client");
        }
        //
        if (enableRandomClientId) {
            return "randomClientId_" + new Random().nextInt(1000);
        }
        //
        return clientId;
    }
}
