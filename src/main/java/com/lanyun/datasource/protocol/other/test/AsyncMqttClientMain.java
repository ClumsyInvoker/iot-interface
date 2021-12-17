package com.lanyun.datasource.protocol.other.test;

import com.lanyun.datasource.protocol.mqtt.client.IrbMqttClient;
import com.lanyun.datasource.protocol.mqtt.conf.DefaultMqttClientConfig;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 22:49 2018/7/23
 * @ Description：${description}
 * @ Modified By：
 */
public class AsyncMqttClientMain {

    private static String url = "tcp://47.92.197.201:1883";
    private static String clientId = "irainbow_mqtt_client_001";
    private static String userName = "admin";
    private static String password = "public";
    private static String annotationTopic = "irb/device/data";
    private static String qos = "1";
    private static int threadNum = 2;
    private static int connectionTimeout = 5;
    private static int keepAliveInterval = 600;


    public static void main(String[] args) {
        DefaultMqttClientConfig config = new DefaultMqttClientConfig();
        config.setServerURI(url);
        config.setClientId(clientId);
        config.setUserName(userName);
        config.setPassWord(password);
        config.setAttentionTopics(annotationTopic);
        config.setQos(qos);
        config.setThreadNum(threadNum);
        config.setConnectionTimeout(connectionTimeout);
        config.setKeepAliveInterval(keepAliveInterval);
        //
        MqttCallbackExtended callback = new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                System.out.println("mqtt connection complete on server " + serverURI);
            }

            @Override
            public void connectionLost(Throwable cause) {
                cause.printStackTrace();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("receive topic:"+topic + ", message:" + message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("message complete :" + token);
            }
        };
        IrbMqttClient client = new IrbMqttClient(config, callback);
        client.start();
    }

}
