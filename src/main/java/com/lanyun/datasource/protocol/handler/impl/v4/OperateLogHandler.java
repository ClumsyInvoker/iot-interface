package com.lanyun.datasource.protocol.handler.impl.v4;

import com.lanyun.datasource.protocol.handler.DeviceMessageHandlerFactory;
import com.lanyun.datasource.protocol.handler.impl.AbstractMessageHandler;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import com.lanyun.datasource.protocol.cmd.Version_4_CmdEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 设备上传日志信息处理器
 *
 * @author wanghu
 * @date 2021-01-02
 * @cmd 3195
 */
@Component
public class OperateLogHandler extends AbstractMessageHandler {

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
                Version_4_CmdEnum.UP_DEVICE_LOG.equals(Version_4_CmdEnum.getCmd(input.getCmd()));
    }
}
