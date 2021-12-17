package com.lanyun.datasource.protocol.handler.impl;

import com.lanyun.datasource.protocol.handler.DeviceMessageHandlerFactory;
import com.lanyun.datasource.protocol.cmd.DeviceCmdEnum;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 扫码开门响应处理器
 * 只针对：U040119110001，U040119110002
 */
@Component
public class DeviceOpenDoorAnswerHandler extends AbstractMessageHandler {

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
        return input != null && DeviceCmdEnum.OPEN_DOOR_ANSWER.getCmd() == input.getCmd();
    }
}

