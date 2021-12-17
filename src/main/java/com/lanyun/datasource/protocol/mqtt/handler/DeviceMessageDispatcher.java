package com.lanyun.datasource.protocol.mqtt.handler;

import com.alibaba.fastjson.JSON;
import com.lanyun.datasource.entity.OriginalDeviceMessage;
import com.lanyun.datasource.enums.TopicEnum;
import com.lanyun.datasource.protocol.handler.DeviceMessageHandler;
import com.lanyun.datasource.protocol.handler.DeviceMessageHandlerFactory;
import com.lanyun.datasource.protocol.other.mock.MockDeviceWatcher;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import com.lanyun.datasource.protocol.netty.codec.json.JsonMessageDecoder;
import com.lanyun.datasource.protocol.netty.codec.json.MessageDecoder;
import com.lanyun.datasource.redis.manage.MqttMessageCache;
import com.lanyun.datasource.service.OriginalDeviceMessageService;
import com.lanyun.datasource.utils.EnumUtil;
import com.lanyun.datasource.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MQTT消息转发器
 */
@Slf4j
@Component
public class DeviceMessageDispatcher {

    private MessageDecoder<DeviceMessage> messageDecoder = new JsonMessageDecoder();

    @Autowired
    private AmqpTemplate deviceMqTemplate;
    @Autowired
    private AmqpTemplate oceanMqTemplate;
    @Autowired
    private AmqpTemplate wasteMqTemplate;
    @Autowired
    private SchedulingTaskExecutor mqttMessageThreadPool;
    @Autowired(required = false)
    private MockDeviceWatcher mockDeviceWatcher;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    private MqttMessageCache mqttMessageCache;
    @Autowired
    private OriginalDeviceMessageService deviceMessageService;

    public void dispatchMessage(String topic, MqttMessage message) {
        mqttMessageThreadPool.execute(() -> doDispatchMessage(topic, message));
    }

    private void doDispatchMessage(String topic, MqttMessage message) {
        // 监测消息不做处理
        if (mockDeviceWatcher != null && mockDeviceWatcher.isMockDeviceMsg(topic, message)) {
            return;
        }
        // 数据是JSON格式，根据数据中的命令id解析出不同的数据结构体
        DeviceMessage deviceMessage = messageDecoder.decode(message.getPayload());
        if (deviceMessage == null) {
            return;
        }

        //check duplicate
        if (isDuplicateMessage(deviceMessage.getMachNo(), message)) {
            log.error("收到重复的 MQTT 消息: " + JsonUtil.toJson(deviceMessage));
           return;
        }

        // 保存原始数据到mongo数据库的original_device_message表
        executorService.execute(() -> {
            OriginalDeviceMessage insert = new OriginalDeviceMessage();
            insert.setVersion(deviceMessage.getVersion());
            insert.setMsgNo(deviceMessage.getMsgNo());
            insert.setMachNo(deviceMessage.getMachNo());
            insert.setCmd(deviceMessage.getCmd());
            insert.setTime(deviceMessage.getTime());
            insert.setData(JSON.toJSONString(deviceMessage.getData()));
            insert.setCreateTime(System.currentTimeMillis());
            try {
                deviceMessageService.insert(insert);
            } catch (Exception e) {
                log.error("数据插入 MongoDB 失败:" + JsonUtil.toJson(deviceMessage), e);
            }
        });

        DeviceMessageHandler<DeviceMessage> handler = DeviceMessageHandlerFactory.getHandler(deviceMessage);
        if (handler == null) {
            log.error("未找到对应的handler：" + JSON.toJSONString(deviceMessage));
            return;
        }
        DeviceMessage result = handler.handle(deviceMessage);
        if (result == null) {
            return;
        }
        // 根据发送的topic来处理对应的消息
        TopicEnum topicEnum = EnumUtil.getByCode(topic, TopicEnum.class);
        String routingKey;
        if (topicEnum == null) {
            log.error("未找到对应topic：" + topic);
            return;
        }

        switch (topicEnum) {
            case JIXIE_DATA:
            case JIXIE_STATE:
                routingKey = "device-message-notify";
                break;
            case HAIYOU_DATA:
            case HAIYOU_STATE:
                routingKey = "haiyou-device-message";
                break;
            case OCEAN_DATA:
            case OCEAN_STATE:
                routingKey = "ocean-device-message";
                break;
            case WASTE_DATA:
            case WASTE_STATE:
                routingKey = "waste-device-message";
                break;
            default:
                return;
        }

        try {
            sendMqMessage(routingKey,JsonUtil.toJson(deviceMessage));
        } catch (AmqpException e) {
            log.error("rabbitMqTemplate发送RabbitMQ消息失败，{}",e);
        }
    }

    private boolean isDuplicateMessage(String serialNo, MqttMessage message) {
        //同一台设备的前一条消息。redis中1个设备1个key
        byte[] previous = mqttMessageCache.getFromCache(serialNo);
        boolean duplicate = previous != null && Arrays.equals(previous, message.getPayload());
        //保持最新一条消息，供下一次检测使用
        if (!duplicate) {
            mqttMessageCache.saveToCache(serialNo, message.getPayload());
        }
        return duplicate;
    }

    private void sendMqMessage(String routingKey,String deviceMessage){
        log.info("[RabbitMQ]发送消息到 device 服务，queue: {}, message: {}", routingKey, deviceMessage);
        switch (routingKey){
            case "device-message-notify":
                deviceMqTemplate.convertAndSend(routingKey, deviceMessage);
                break;
            // 这里收到海油1的设备消息，在发给海油1 device处理的同事也需要发给海油2 device进行处理
            // 海油1设备（U040119110001，U040119110002）联网认证机制未知，所以无法通过认证修改topic
            // 这里只能保留此topic进行数据处理，待海油1设备取消时剔除
            case "haiyou-device-message":
                deviceMqTemplate.convertAndSend(routingKey, deviceMessage);
            case "ocean-device-message":
                oceanMqTemplate.convertAndSend(routingKey, deviceMessage);
                break;
            case "waste-device-message":
                wasteMqTemplate.convertAndSend(routingKey, deviceMessage);
                break;
        }
    }

}
