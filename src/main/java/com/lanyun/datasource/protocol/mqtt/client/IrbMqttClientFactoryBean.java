package com.lanyun.datasource.protocol.mqtt.client;

import com.lanyun.datasource.protocol.mqtt.conf.MqttClientConfig;
import com.lanyun.datasource.protocol.mqtt.handler.DeviceMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.Resource;
import java.util.Timer;
import java.util.TimerTask;

/**
 * MQTT客户端工厂
 */
@Slf4j
public class IrbMqttClientFactoryBean implements FactoryBean<IrbMqttClient>, DisposableBean {

    private MqttClientConfig clientConfig;

    private IrbMqttClient mqttClient;

    private Timer monitorTimer = new Timer("iot_MQTT_connection_monitor", true);

    private TimerTask monitorTask = new TimerTask() {
        @Override
        public void run() {
            if (mqttClient != null && !mqttClient.isConnected()) {
                log.warn("mqtt connection lost, try to reconnect...");
                mqttClient.reconnect();
            }
        }
    };

    @Resource
    private DeviceMessageListener callback;

    @Override
    public IrbMqttClient getObject() throws Exception {
        if (mqttClient == null)
        {
            init();
        }
        return mqttClient;
    }

    @Override
    public Class<?> getObjectType() {
        return IrbMqttClient.class;
    }

    private synchronized void init() {
        if (mqttClient == null)
        {
            mqttClient = new IrbMqttClient(clientConfig, callback);
            mqttClient.start();
            //监控重连，5秒钟检查一次
            monitorTimer.scheduleAtFixedRate(monitorTask, 10*1000, 5*1000);
        }
    }

    public MqttClientConfig getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(MqttClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    @Override
    public void destroy() throws Exception {
        monitorTimer.cancel();
        if (mqttClient != null)
        {
            mqttClient.stop();
        }
    }
}
