package com.lanyun.iot.gateway.controller.mqtt.handler;

import com.alibaba.fastjson.JSON;
import com.lanyun.iot.gateway.model.entity.OriginalDeviceMessage;
import com.lanyun.iot.gateway.model.enums.TopicEnum;
import com.lanyun.iot.gateway.service.handler.DeviceMessageHandler;
import com.lanyun.iot.gateway.service.handler.DeviceMessageHandlerFactory;
import com.lanyun.iot.gateway.model.protocol.other.mock.MockDeviceWatcher;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import com.lanyun.iot.gateway.controller.mqtt.cmd.IotMessage;
import com.lanyun.iot.gateway.controller.netty.codec.json.JsonMessageDecoder;
import com.lanyun.iot.gateway.controller.netty.codec.json.NewJsonMessageDecoder;
import com.lanyun.iot.gateway.controller.netty.codec.json.MessageDecoder;
import com.lanyun.iot.gateway.proxy.CloudWareHouseProxy;
import com.lanyun.iot.gateway.proxy.redis.manage.MqttMessageCache;
import com.lanyun.iot.gateway.service.OriginalDeviceMessageService;
import com.lanyun.iot.gateway.utils.EnumUtil;
import com.lanyun.iot.gateway.utils.JsonUtil;
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
import java.util.UUID;

/**
 * MQTT消息转发器
 */
@Slf4j
@Component
public class DeviceMessageDispatcher {

    private MessageDecoder<IotMessage> messageDecoder = new NewJsonMessageDecoder();

    private MessageDecoder<DeviceMessage> messageDecoder_old = new JsonMessageDecoder();

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

    @Autowired
    private CloudWareHouseProxy cloudWareHouseProxy;

    public void dispatchMessage(String topic, MqttMessage message) {
        mqttMessageThreadPool.execute(() -> doDispatchMessage(topic, message));
    }

    public void dispatchMessage_old(String topic, MqttMessage message) {
        mqttMessageThreadPool.execute(() -> doDispatchMessage_old(topic, message));
    }

    private void doDispatchMessage(String topic, MqttMessage message) {
        // 监测消息不做处理
        if (mockDeviceWatcher != null && mockDeviceWatcher.isMockDeviceMsg(topic, message)) {
            return;
        }

        log.debug("MQTT Dispatch Testing.");

        // 数据是JSON格式，根据数据中的命令id解析出不同的数据结构体
        IotMessage iotMessage = messageDecoder.decode(message.getPayload());
        if (iotMessage == null) {
            return;
        }

        //check duplicate
        if (isDuplicateMessage(iotMessage.getSerialNo(), message)) {
            log.error("收到重复的 MQTT 消息: " + JsonUtil.toJson(iotMessage));
           return;
        }

        // 保存原始数据到mongo数据库的original_device_message表
        /*
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
        */

        // 获得topic名称
        TopicEnum topicEnum = EnumUtil.getByCode(topic, TopicEnum.class);
        if (topicEnum == null) {
            log.error("未找到对应topic：" + topic);
            return;
        }
        iotMessage.setTopic(topic);

        log.warn("尝试发送Json串：{}",JsonUtil.toJson(iotMessage));
        try {
            cloudWareHouseProxy.handleIotMessage(JsonUtil.toJson(iotMessage));
        } catch (AmqpException e) {
            log.error("请求CloudWareHouse处理消息失败，{}",e);
        }
    }

    private void doDispatchMessage_old(String topic, MqttMessage message) {
        // 监测消息不做处理
        if (mockDeviceWatcher != null && mockDeviceWatcher.isMockDeviceMsg(topic, message)) {
            return;
        }
        // 数据是JSON格式，根据数据中的命令id解析出不同的数据结构体
        DeviceMessage deviceMessage = messageDecoder_old.decode(message.getPayload());
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
            cloudWareHouseProxy.handleIotMessage(JsonUtil.toJson(deviceMessage));
        } catch (AmqpException e) {
            log.error("请求CloudWareHouse处理消息失败，{}",e);
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

}
