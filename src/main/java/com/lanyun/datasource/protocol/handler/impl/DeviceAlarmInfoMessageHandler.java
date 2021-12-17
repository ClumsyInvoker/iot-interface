package com.lanyun.datasource.protocol.handler.impl;

import com.lanyun.datasource.protocol.handler.DeviceMessageHandlerFactory;
import com.lanyun.datasource.protocol.cmd.DeviceCmdEnum;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 3130
 * 设备预警处理器
 */
@Component
public class DeviceAlarmInfoMessageHandler extends AbstractMessageHandler {

    @PostConstruct
    @Override
    public void init() {
        DeviceMessageHandlerFactory.registry(this);
        super.initialize();
        // TODO 添加公用的filter类
//        super.addInterceptor(new EmptyMessageFilter());
    }

    @Override
    protected boolean doFilter(DeviceMessage deviceMessage) {
        return false;
    }

    @Override
    public boolean support(DeviceMessage input) {
        return input != null && DeviceCmdEnum.getCmd(input.getCmd()) == DeviceCmdEnum.UP_DEVICE_ALARM;
    }
}
