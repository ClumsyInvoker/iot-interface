package com.lanyun.iot.gateway.service.handler;

import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息处理器工厂
 */
@Slf4j
public class DeviceMessageHandlerFactory {

    private static final List<DeviceMessageHandler<DeviceMessage>> DEVICE_MESSAGE_HANDLERS = new ArrayList<>();

    public static void registry(DeviceMessageHandler<DeviceMessage> handler) {
        DEVICE_MESSAGE_HANDLERS.add(handler);
    }

    public static DeviceMessageHandler<DeviceMessage> getHandler(DeviceMessage input) {
        List<DeviceMessageHandler<DeviceMessage>> handlers = DEVICE_MESSAGE_HANDLERS.stream().filter(e -> e.support(input)).collect(Collectors.toList());
        //不存在
        if (handlers == null || handlers.size() == 0) {
            return null;
        }
        //正好存在1个
        if (handlers.size() == 1) {
            return handlers.get(0);
        }
        //存在多个
        return new CompositeDeviceMessageHandler(handlers);
    }

    /**
     * 组合Handler，这种情况至多允许1个Handler有返回值，否则报错
     */
    static class CompositeDeviceMessageHandler implements DeviceMessageHandler<DeviceMessage> {

        private List<DeviceMessageHandler<DeviceMessage>> handlers;

        CompositeDeviceMessageHandler(List<DeviceMessageHandler<DeviceMessage>> handlers) {
            this.handlers = handlers;
        }

        @Override
        public void init() {
            //nothing to do
        }

        @Override
        public DeviceMessage handle(DeviceMessage input) {
            DeviceMessage output = null;
            for (DeviceMessageHandler<DeviceMessage> handler : handlers) {
                DeviceMessage ret = handler.handle(input);
                if (ret != null) {
                    if (output == null) {
                        output = ret;
                    } else {
                        throw new IllegalStateException("CompositeDeviceMessageHandler 仅允许1个Handler有返回值，handlers:" + Arrays.toString(handlers.toArray()));
                    }
                }
            }
            return output;
        }

        @Override
        public boolean support(DeviceMessage input) {
            return false;
        }

        @Override
        public DeviceMessage process(DeviceMessage input) {
            return null;
        }
    }
}
