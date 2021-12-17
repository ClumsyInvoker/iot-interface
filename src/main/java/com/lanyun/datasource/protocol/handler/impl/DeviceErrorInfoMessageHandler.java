package com.lanyun.datasource.protocol.handler.impl;

import com.lanyun.datasource.protocol.handler.DeviceMessageHandlerFactory;
import com.lanyun.datasource.protocol.cmd.DeviceCmdEnum;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 3131
 * 设备故障上报处理器
 */
@Component
public class DeviceErrorInfoMessageHandler extends AbstractMessageHandler {

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
        return input != null && DeviceCmdEnum.getCmd(input.getCmd()) == DeviceCmdEnum.UP_DEVICE_ERROR;
    }
}
