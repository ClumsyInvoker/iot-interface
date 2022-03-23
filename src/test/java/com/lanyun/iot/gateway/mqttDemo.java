package com.lanyun.iot.gateway;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.LinkedHashMap;
import org.json.JSONObject;
import org.json.JSONException;

public class mqttDemo {

    public static final String HOST = "tcp://127.0.0.1:1833";
    public static final String TOPIC = "test";
    private static final String clientid = "testClient";
    private MqttClient client;
    private MqttConnectOptions options;
    private String userName = "admin";
    private String passWord = "password";
    private String Message;

    private void start() {
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置连接的用户名
            options.setUserName(userName);
            // 设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(5);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(600);
            // 设置回调
            client.setCallback(new PushCallback());

            MqttTopic topic = client.getTopic(TOPIC);
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            // options.setWill(topic, "close".getBytes(), 2, true);

            client.connect(options);
            //订阅消息
            int[] Qos  = {1};
            String[] topic1 = {TOPIC};
            client.subscribe(topic1, Qos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(){
        byte[] payload = Message.getBytes();
        int qos = 1;
        try {
            client.publish(TOPIC, payload, qos, false);
        } catch (MqttException e){
            e.printStackTrace();
        }
    }

    private void generateMessage(){
        JSONObject object = new JSONObject(new LinkedHashMap());
        try {
            object.put("id", 1);
            object.put("ts", 1);
            object.put("serialNo", "1");
            object.put("productId", 1);
            object.put("method", "service");
            object.put("data", null);
        } catch (JSONException e){
            e.printStackTrace();
        }
        Message = object.toString();
    }

    public static void main(String[] args) throws MqttException {
        mqttDemo demo = new mqttDemo();
        demo.start();
        demo.generateMessage();
        demo.sendMessage();
    }
}
