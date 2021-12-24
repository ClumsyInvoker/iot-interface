package com.lanyun.iot.gateway.controller.netty.handler;

import com.lanyun.iot.gateway.model.exception.DeviceMessageException;
import com.lanyun.iot.gateway.service.handler.DeviceBinaryMessageHandlerFactory;
import com.lanyun.iot.gateway.service.handler.DeviceMessageHandler;
import com.lanyun.iot.gateway.model.protocol.data.upgrade.DeviceBinaryMsg;
import com.lanyun.iot.gateway.service.DeviceMessageService;
import com.lanyun.iot.gateway.utils.JsonUtil;
import com.lanyun.iot.gateway.utils.MySpringUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 23:19 2018/9/26
 * @ Description：${description}
 * @ Modified By：
 */
@Slf4j
public class DeviceUpgradeHandler extends SimpleChannelInboundHandler<DeviceBinaryMsg> {

    private DeviceMessageService deviceMessageService = MySpringUtils.getBean(DeviceMessageService.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DeviceBinaryMsg msg) throws Exception {
        DeviceMessageHandler<DeviceBinaryMsg> handler = DeviceBinaryMessageHandlerFactory.getHandler(msg);
        if (handler == null) {
            throw new DeviceMessageException("unsupported device binary msg: " + JsonUtil.toJson(msg));
        }

        if (log.isDebugEnabled()) {
            log.debug("send response for channel " + ctx.channel());
        }
        DeviceBinaryMsg response = handler.process(msg);
        if (response != null) {
            ctx.writeAndFlush(response);
        }
        deviceMessageService.add(msg);
    }
}
