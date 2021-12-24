package com.lanyun.iot.gateway.service.handler.impl;

import com.lanyun.iot.gateway.service.handler.DeviceMessageHandlerFactory;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceCmdEnum;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 3172:
 * 每天的定时上报（只取Y值）， 用于台账统计,
 * Y值为 remain, 配合3171的 consume, 计算出当天的 produce.
 */
@Component
public class DeviceWasteWeightScheduledMessageHandler extends AbstractMessageHandler{

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
        return input != null && DeviceCmdEnum.getCmd(input.getCmd()) == DeviceCmdEnum.UP_DEVICE_WEIGHT_SCHEDULED_DATA;
    }
}
