package com.lanyun.iot.gateway.service.handler.impl;

import com.lanyun.iot.gateway.service.handler.DeviceMessageHandlerFactory;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceCmdEnum;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 3171:
 * 危废重量减少上报, 用于正常的重量减少，
 * 实际的触发生成联单操作，参与台账统计.
 */
@Component
public class DeviceWasteWeightReducedMessageHandler extends AbstractMessageHandler {

    @PostConstruct
    @Override
    public void init() {
        DeviceMessageHandlerFactory.registry(this);
        super.initialize();
    }

    @Override
    protected boolean doFilter(DeviceMessage deviceMessage) {
        return false;
    }

    @Override
    public boolean support(DeviceMessage input) {
        return input != null && DeviceCmdEnum.getCmd(input.getCmd()) == DeviceCmdEnum.UP_DEVICE_WEIGHT_REDUCED_DATA;
    }
}
