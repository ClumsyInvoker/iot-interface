package com.lanyun.datasource.protocol.handler.impl.v4;

import com.lanyun.datasource.protocol.handler.DeviceMessageHandlerFactory;
import com.lanyun.datasource.protocol.handler.impl.AbstractMessageHandler;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import com.lanyun.datasource.protocol.cmd.Version_4_CmdEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 下发远程控制设备响应处理器
 *
 * @author wanghu
 * @date 2020-12-30
 * @cmd 3190
 */
@Component
public class RemoteControlResponseHandler extends AbstractMessageHandler {

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
        return input != null &&
                Version_4_CmdEnum.DOWN_REMOTE_CONTROL_RESPONSE.equals(Version_4_CmdEnum.getCmd(input.getCmd()));
    }
}
