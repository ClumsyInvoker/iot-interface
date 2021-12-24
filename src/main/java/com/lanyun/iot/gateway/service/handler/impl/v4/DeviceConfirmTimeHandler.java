package com.lanyun.iot.gateway.service.handler.impl.v4;

import com.lanyun.iot.gateway.service.handler.DeviceMessageHandlerFactory;
import com.lanyun.iot.gateway.service.handler.impl.AbstractMessageHandler;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import com.lanyun.iot.gateway.controller.mqtt.cmd.Version_4_CmdEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 设备定时校准本地时间处理器
 *
 * @author wanghu
 * @date 2020-12-29
 * @cmd 3154
 */
@Component
public class DeviceConfirmTimeHandler extends AbstractMessageHandler {

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
        return input != null
                && Version_4_CmdEnum.UP_TIME_VALIDATE_REQUEST.equals(Version_4_CmdEnum.getCmd(input.getCmd()));
    }
}
