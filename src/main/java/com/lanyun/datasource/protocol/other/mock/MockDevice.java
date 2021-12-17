package com.lanyun.datasource.protocol.other.mock;

import com.lanyun.datasource.protocol.mqtt.conf.MqttClientConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 21:18 2018/7/6
 * @ Description：${description}
 * @ Modified By：
 */
@Slf4j
@Data
public class MockDevice implements InitializingBean, DisposableBean {

    private MqttClientConfig clientConfig;

    private boolean enable;

    private String topicPrefix;

    private String clientId;

    //////////////////以下字段不要依赖注入
    private volatile boolean start = true;

    private Thread t;


    private void run() {

        log.info("starting mockDevice ... ");

        String topic = topicPrefix + clientId;
        String contentPrefix = "ping_test";
        int qos = 1;
        String broker = clientConfig.getServerURI();
        String clientId = this.clientId;
        String userName = clientConfig.getUserName();
        String passWord = clientConfig.getPassWord();
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(userName);
            connOpts.setPassword(passWord.toCharArray());
            sampleClient.connect(connOpts);
            sampleClient.setTimeToWait(5 * 1000); //5秒超时
            //send
            while (start) {
                try {
                    String content = contentPrefix + System.currentTimeMillis();
                    MqttMessage message = new MqttMessage(content.getBytes());
                    message.setQos(qos);
                    log.debug("mockDevice sending message: " + content);
                    sampleClient.publish(topic, message);
                } catch (Exception e) {
                    log.error("mockDevice sent message error", e);
                }
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    log.debug("mockDevice thread was interrupted from sleep");
                }
            }
            //
            log.info("stopping MockDevice ... ");
            sampleClient.disconnect();
        } catch (MqttException me) {

            log.error("MockDevice connect server failed", me);
        }
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        if (enable)
        {
            log.info("mock device is set to enable, will be start");
            Thread t = new Thread(this::run);
            t.setDaemon(true);
            t.start();
        }
    }

    @Override
    public void destroy() throws Exception {
        log.info("dispose mockDevice, will be stop");
        this.start = false;
        if (t != null)
            t.interrupt();
    }
}
