package com.lanyun.iot.gateway.service.handler.impl;

import com.lanyun.iot.gateway.service.handler.DeviceMessageHandler;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import com.lanyun.iot.gateway.model.protocol.data.upgrade.DeviceBinaryMsg;
import org.springframework.stereotype.Component;

/**
 * 二进制消息处理抽象接口
 */
@Component
public abstract class AbstractBinaryMessageHandler implements DeviceMessageHandler<DeviceBinaryMsg> {

    protected abstract boolean doFilter(DeviceMessage deviceMessage);


    /**
     * null表示filter验证没过
     */
    @Override
    public DeviceBinaryMsg handle(DeviceBinaryMsg input) {
        return null;
    }

}
