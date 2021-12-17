package com.lanyun.datasource.protocol.handler.impl;

import com.lanyun.datasource.protocol.handler.DeviceMessageHandlerFactory;
import com.lanyun.datasource.protocol.cmd.DeviceCmdEnum;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 1122
 * 上报升级包结果处理器
 */
@Component
public class UpgradeResultReportHandler extends AbstractMessageHandler {

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
        return input != null && DeviceCmdEnum.UP_UPGRADE_RESULT_REQ.getCmd() == input.getCmd();
    }
}
