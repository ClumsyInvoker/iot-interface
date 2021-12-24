package com.lanyun.iot.gateway.controller.rabbitmq;

import com.lanyun.iot.gateway.model.enums.TopicEnum;
import com.lanyun.iot.gateway.controller.mqtt.sender.WaitForCompletionMqttCmdSender;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import com.lanyun.iot.gateway.controller.netty.codec.json.JsonMessageDecoder;
import com.lanyun.iot.gateway.controller.netty.codec.json.MessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-09-27 19:35
 */
@Component
@Slf4j
public class MqttCmdSendListener {

    @Resource
    private WaitForCompletionMqttCmdSender lockCmdSender;

    private MessageDecoder<DeviceMessage> messageDecoder = new JsonMessageDecoder();

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("device-mqtt-send"), containerFactory = "deviceMqContainerFactory")
    public void jixieProcess(String message) {
        sendDeviceMessage(message, TopicEnum.JIXIE_DEVICE.getCode());
    }

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("haiyou-device-send"), containerFactory = "deviceMqContainerFactory")
    public void haiyouProcess(String message) {
        sendDeviceMessage(message, TopicEnum.HAIYOU_DEVICE.getCode());
    }

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("ocean-device-send"), containerFactory = "oceanMqContainerFactory")
    public void oceanProcess(String message) {
        // 海油2和江苏危废设备订阅消息topic需要额外处理，设备长度有限制（非海油不用转换）
        sendDeviceMessage(message, TopicEnum.OCEAN_DEVICE.getCode());
    }

    private void sendDeviceMessage(String message, String topic) {
        log.info("待下发MQTT设备消息 msg={}", message);
        DeviceMessage deviceMessage;
        try {
            deviceMessage = messageDecoder.decode(message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("下发设备消息解码失败", e);
            return;
        }
        if (deviceMessage == null) {
            log.error("下发设备消息解码为null");
            return;
        }
        if (TopicEnum.OCEAN_DEVICE.getCode().equals(topic) && !deviceMessage.getMachNo().startsWith("U04")){
            topic =TopicEnum.OCEAN_DEVICE_1.getCode();
        }

            try {
                lockCmdSender.send(topic, deviceMessage.getMachNo(), deviceMessage);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("mqtt下发命定失败：", e);
            }
    }
}
