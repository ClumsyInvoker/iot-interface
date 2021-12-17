package com.lanyun.datasource.protocol.handler.impl;

import com.lanyun.datasource.protocol.handler.DeviceMessageHandler;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import com.lanyun.datasource.protocol.data.upgrade.DeviceBinaryMsg;
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
