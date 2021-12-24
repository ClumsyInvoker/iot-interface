package com.lanyun.iot.gateway.service.handler.impl.v4;

import com.lanyun.iot.gateway.service.handler.DeviceMessageHandlerFactory;
import com.lanyun.iot.gateway.service.handler.impl.AbstractMessageHandler;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import com.lanyun.iot.gateway.controller.mqtt.cmd.Version_4_CmdEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 油水分离器预处理结果上报处理器
 *
 * @author wanghu
 * @date 2021-01-02
 * @cmd 3197
 */
@Component
public class SeparateResultHandler extends AbstractMessageHandler {

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
                Version_4_CmdEnum.UP_SEPARATE_RESULT.equals(Version_4_CmdEnum.getCmd(input.getCmd()));
    }
}
