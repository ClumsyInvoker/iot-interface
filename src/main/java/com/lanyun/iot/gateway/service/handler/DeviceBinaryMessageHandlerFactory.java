package com.lanyun.iot.gateway.service.handler;

import com.lanyun.iot.gateway.model.protocol.data.upgrade.DeviceBinaryMsg;
import com.lanyun.iot.gateway.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 二进制消息处理器工厂：
 */
@Slf4j
public class DeviceBinaryMessageHandlerFactory {

    private static final List<DeviceMessageHandler<DeviceBinaryMsg>> DEVICE_MESSAGE_HANDLERS = new ArrayList<>();

    public static void registry(DeviceMessageHandler<DeviceBinaryMsg> handler) {
        log.info("registry deviceMessageHandler: " + handler.getClass().getName());
        DEVICE_MESSAGE_HANDLERS.add(handler);
    }

    public static DeviceMessageHandler<DeviceBinaryMsg> getHandler(DeviceBinaryMsg input) {
        List<DeviceMessageHandler<DeviceBinaryMsg>> handlers = DEVICE_MESSAGE_HANDLERS.stream().filter(e -> e.support(input)).collect(Collectors.toList());
        //不存在
        if (handlers == null || handlers.size() == 0) {
            return null;
        }
        //正好存在1个
        if (handlers.size() == 1) {
            return handlers.get(0);
        }
        //存在多个
        throw new IllegalStateException("there has more than one deviceBinaryHandler for message : " + JsonUtil.toJson(input));
    }
}
