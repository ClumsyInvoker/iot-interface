package com.lanyun.iot.gateway.controller.netty.handler;

import com.alibaba.fastjson.JSON;
import com.lanyun.iot.gateway.model.enums.MessageVersionEnum;
import com.lanyun.iot.gateway.controller.filter.WasteCollectFilter;
import com.lanyun.iot.gateway.service.handler.DeviceMessageHandler;
import com.lanyun.iot.gateway.service.handler.DeviceMessageHandlerFactory;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceCmdEnum;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import com.lanyun.iot.gateway.model.protocol.data.down.AuthResponse;
import com.lanyun.iot.gateway.controller.routers.DeviceRouter;
import com.lanyun.iot.gateway.controller.routers.DeviceVersion4Router;
import com.lanyun.iot.gateway.controller.routers.DeviceWasteRouters;
import com.lanyun.iot.gateway.service.DeviceMessageService;
import com.lanyun.iot.gateway.utils.HttpUtil;
import com.lanyun.iot.gateway.utils.JsonUtil;
import com.lanyun.iot.gateway.utils.MySpringUtils;
import com.lanyun.iot.gateway.proxy.CloudWareHouseProxy;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 15:17 2018/6/17
 * @ Description：${description}
 * @ Modified By：
 */
@Slf4j
public class NettyAuthMessageHandler extends SimpleChannelInboundHandler<DeviceMessage> {

    private CloudWareHouseProxy cloudWareHouseProxy = MySpringUtils.getBean(CloudWareHouseProxy.class);

    private DeviceRouter deviceRouter = MySpringUtils.getBean(DeviceRouter.class);

    private DeviceVersion4Router oceanRouter = MySpringUtils.getBean(DeviceVersion4Router.class);

    private DeviceMessageService deviceMessageService = MySpringUtils.getBean(DeviceMessageService.class);

    private WasteCollectFilter wasteCollectFilter = MySpringUtils.getBean(WasteCollectFilter.class);

    private DeviceWasteRouters deviceWasteRouters = MySpringUtils.getBean(DeviceWasteRouters.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
        Channel incoming = ctx.channel();
        log.error("NettyClientMain:" + incoming.remoteAddress() + "异常", cause);
        // 当出现异常就关闭连接
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DeviceMessage msg) throws Exception {
        if (DeviceCmdEnum.UP_AUTH.getCmd() != msg.getCmd()) {
            ctx.fireChannelRead(msg);
            log.error("cmd {} not supported, ", msg.getCmd());
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("收到设备认证消息：" + JsonUtil.toJson(msg));
        }
        DeviceMessageHandler<DeviceMessage> handler = DeviceMessageHandlerFactory.getHandler(msg);
        if (handler == null) {
            log.error("no handle for message: " + JsonUtil.toJson(msg));
            return;
        }
        //处理
        DeviceMessage filter = handler.handle(msg);
        if (filter == null) {
            log.error("msg {} filter failed", msg.getMsgNo());
            return;
        }
        DeviceMessage response = cloudWareHouseProxy.deviceAuth(msg);
        response = JSON.parseObject(JSON.toJSONString(response.getData()), DeviceMessage.class);  
        response.setData(JSON.parseObject(JSON.toJSONString(response.getData()), AuthResponse.class));

        if (log.isDebugEnabled()) {
            log.debug("response auth message:" + JsonUtil.toJson(response));
        }
        //deviceMessageService.add(msg);
        ctx.writeAndFlush(response);
    }

}