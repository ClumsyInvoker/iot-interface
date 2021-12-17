package com.lanyun.datasource.protocol.netty.handler;

import com.lanyun.datasource.exception.DeviceMessageException;
import com.lanyun.datasource.protocol.handler.DeviceBinaryMessageHandlerFactory;
import com.lanyun.datasource.protocol.handler.DeviceMessageHandler;
import com.lanyun.datasource.protocol.data.upgrade.DeviceBinaryMsg;
import com.lanyun.datasource.service.DeviceMessageService;
import com.lanyun.datasource.utils.JsonUtil;
import com.lanyun.datasource.utils.MySpringUtils;
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
