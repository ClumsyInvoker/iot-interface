package com.lanyun.datasource.protocol.handler.impl.v4;

import com.lanyun.datasource.protocol.handler.DeviceMessageHandlerFactory;
import com.lanyun.datasource.protocol.handler.impl.AbstractMessageHandler;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import com.lanyun.datasource.protocol.cmd.Version_4_CmdEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 设备上报预警信息处理器
 *
 * @author wanghu
 * @date 2020-12-31
 * @cmd 3190
 */
@Component
public class DeviceWarningHandler extends AbstractMessageHandler {

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
        return input != null &&
                Version_4_CmdEnum.UP_ALARM_MESSAGE.equals(Version_4_CmdEnum.getCmd(input.getCmd()));
    }
}
