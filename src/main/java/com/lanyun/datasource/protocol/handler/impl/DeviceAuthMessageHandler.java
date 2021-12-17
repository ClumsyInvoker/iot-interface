package com.lanyun.datasource.protocol.handler.impl;

import com.lanyun.datasource.protocol.handler.DeviceMessageHandlerFactory;
import com.lanyun.datasource.protocol.cmd.DeviceCmdEnum;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 1110
 * 设备认证处理器
 */
@Component
public class DeviceAuthMessageHandler extends AbstractMessageHandler {

    @PostConstruct
    @Override
    public void init() {
        DeviceMessageHandlerFactory.registry(this);
        super.initialize();
    }

    @Override
    public boolean support(DeviceMessage input) {
        return input != null && DeviceCmdEnum.UP_AUTH.getCmd() == input.getCmd();
    }

    @Override
    protected boolean doFilter(DeviceMessage deviceMessage) {
        return false;
    }
}
