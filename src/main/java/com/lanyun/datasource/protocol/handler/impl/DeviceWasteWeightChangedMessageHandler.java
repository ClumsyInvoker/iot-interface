package com.lanyun.datasource.protocol.handler.impl;

import com.lanyun.datasource.protocol.handler.DeviceMessageHandlerFactory;
import com.lanyun.datasource.protocol.cmd.DeviceCmdEnum;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 3170:
 * 危废重量变化上报, 包含重量增加和小幅减少两种情况（只取Y值）。
 * 该上报只用于设备的临时重量展示，不参与台账统计.
 */
@Component
public class DeviceWasteWeightChangedMessageHandler extends AbstractMessageHandler {

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
        return input != null && DeviceCmdEnum.getCmd(input.getCmd()) == DeviceCmdEnum.UP_DEVICE_WEIGHT_CHANGED_DATA;
    }
}
