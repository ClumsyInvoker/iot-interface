package com.lanyun.iot.gateway.model.bean;

import com.lanyun.iot.gateway.controller.mqtt.client.IrbMqttClientFactoryBean;
import com.lanyun.iot.gateway.controller.mqtt.conf.MqttClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-27 14:51
 */
@Component
@Order(2)
public class InitIrbMqttClientFactoryBean {

    @Autowired
    private MqttClientConfig config;

    @Bean
    public IrbMqttClientFactoryBean irbMqttClientFactoryBean() {
        IrbMqttClientFactoryBean bean = new IrbMqttClientFactoryBean();
        bean.setClientConfig(config);
        return bean;
    }
}
