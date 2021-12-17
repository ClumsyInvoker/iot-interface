package com.lanyun.datasource.protocol.mqtt.client;

import com.lanyun.datasource.protocol.mqtt.conf.MqttClientConfig;
import com.lanyun.datasource.protocol.mqtt.handler.DeviceMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * MQTT客户端
 */
@Slf4j
public class IrbMqttClient {

    /**
     * 最小线程数为3，测试发现线程数小于三会无法接收响应，原因未知
     */
    private static final int MIN_THREAD_NUM = 5;
    /**
     * MQTT客户端
     */
    private MqttAsyncClient client;

    private MqttClientConfig config;

    private MqttCallbackExtended callback;

    private ScheduledExecutorService scheduler;

    public IrbMqttClient(MqttClientConfig config, MqttCallbackExtended callback) {
        this.config = config;
        this.callback = callback;
        int threadNum = config.getThreadNum();
        if (threadNum < MIN_THREAD_NUM) {
            threadNum = MIN_THREAD_NUM;
        }
        this.scheduler = new ScheduledThreadPoolExecutor(threadNum);
        //
        if (callback instanceof DeviceMessageListener) {
            ((DeviceMessageListener)callback).setClient(this);
        }
    }

    public synchronized void start() {
        if (client == null) {
            client = doStartAsyncMqttClient(config, callback, scheduler);
        }
    }

    public synchronized void stop() {
        if (client != null) {
            try {
                //200ms超时
                client.disconnect().waitForCompletion(200);
            } catch (Exception e) {
                log.error("failed to disconnected mqtt client", e);
            }
            try {

                client.close(true);
            } catch (MqttException e) {
                log.error("failed to close mqtt client", e);
            }
            scheduler.shutdown();
        }
    }

    private MqttAsyncClient doStartAsyncMqttClient(MqttClientConfig config, MqttCallback callback,
                                                   ScheduledExecutorService scheduler)
    {
        MqttAsyncClient client = null;
        log.info("mqtt client start connecting to server:" + config.getServerURI());
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttAsyncClient(config.getServerURI(), config.getRandomClientId(),
                    new MemoryPersistence(), new TimerPingSender(), scheduler);
//            client = new MqttAsyncClient(config.getServerURI(), config.getClientId(), new MemoryPersistence());
            // MQTT的连接设置
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);
            // 设置连接的用户名
            options.setUserName(config.getUserName());
            // 设置连接的密码
            options.setPassword(config.getPassWord().toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(config.getConnectionTimeout());
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(config.getKeepAliveInterval());
            // 设置回调
            client.setCallback(callback);

            client.connect(options).waitForCompletion(5 * 1000);

            log.info("mqtt client connected");


        } catch (Exception e) {
            log.error("Mqtt client start failed, closing the client...", e);
            if (client != null) {
                try {
                    client.close(true);
                } catch (MqttException e1) {
                    log.error("failed to close mqtt client", e1);
                }
                client = null;
            }
        }
        return client;
    }

    //订阅消息
    public void subscribeTopics()
    {
        try
        {
            int[] qos = config.getQosArray();
            String[] topic = config.getTopicArray();
            log.info("订阅topic：" + Arrays.toString(topic));
            client.subscribe(topic, qos);
        } catch (Exception e) {
            log.error("error to subscribe topics");
        }
    }

    public IMqttDeliveryToken sendMessage(String topic, MqttMessage mqttMessage) {
        try {
            return client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            log.error("fail to publish mqttMessage for topic " + topic);
            return null;
        }
    }

    public void reconnect() {
        log.warn("MQTT reconnect to server...");
        if (client != null) {
            try {
                client.reconnect();
            } catch (Exception e) {
                log.error("mqtt reconnect error", e);
            }

        } else {
            start();
        }
    }

    public boolean isConnected() {
        return client != null && client.isConnected();
    }
}
