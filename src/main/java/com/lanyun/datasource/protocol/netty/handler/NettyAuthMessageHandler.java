package com.lanyun.datasource.protocol.netty.handler;

import com.alibaba.fastjson.JSON;
import com.lanyun.datasource.enums.MessageVersionEnum;
import com.lanyun.datasource.filter.WasteCollectFilter;
import com.lanyun.datasource.protocol.handler.DeviceMessageHandler;
import com.lanyun.datasource.protocol.handler.DeviceMessageHandlerFactory;
import com.lanyun.datasource.protocol.cmd.DeviceCmdEnum;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import com.lanyun.datasource.protocol.data.down.AuthResponse;
import com.lanyun.datasource.routers.DeviceRouter;
import com.lanyun.datasource.routers.DeviceVersion4Router;
import com.lanyun.datasource.routers.DeviceWasteRouters;
import com.lanyun.datasource.service.DeviceMessageService;
import com.lanyun.datasource.utils.HttpUtil;
import com.lanyun.datasource.utils.JsonUtil;
import com.lanyun.datasource.utils.MySpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 15:17 2018/6/17
 * @ Description：${description}
 * @ Modified By：
 */
@Slf4j
public class NettyAuthMessageHandler extends SimpleChannelInboundHandler<DeviceMessage> {

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
        DeviceMessage response;

        //针对江苏危废收集的单独过滤设备
        if (wasteCollectFilter.doFilter(msg)) {
            log.info("危废收集项目认证信息:" + msg);
            response = HttpUtil.postFromJson(deviceWasteRouters.getMqttDeviceAuth(), msg, DeviceMessage.class);
            response = JSON.parseObject(JSON.toJSONString(response.getData()), DeviceMessage.class);
            response.setData(JSON.parseObject(JSON.toJSONString(response.getData()), AuthResponse.class));
        } else {
            log.info("设备认证非危废逻辑:{}",msg);
            /**
             * @date 2020-12-29
             * 所有项目公用一个dataSource。但是不同平台的设备认证需要到各自具体的服务去认证
             * 所以这里根据消息版本号区分不同的平台，目前只有消息版本号4为新平台
             */
            if (MessageVersionEnum.MESSAGE_VERSION_ENUM_4.getCode().equals(msg.getVersion())
                    || filter.getMachNo().startsWith("U1101000")) {
                // 通用云仓改造款统一去海油系统认证
                response = HttpUtil.postFromJson(oceanRouter.getMqttDeviceAuth(), msg, DeviceMessage.class);
                // 针对现有调试设备单独处理
                if (!msg.getMachNo().equals("U040320120001")) {
                    response = JSON.parseObject(JSON.toJSONString(response.getData()), DeviceMessage.class);
                    response.setData(JSON.parseObject(JSON.toJSONString(response.getData()), AuthResponse.class));
                }
            } else {
                response = HttpUtil.postFromJson(deviceRouter.getMqttDeviceAuth(), msg, DeviceMessage.class);
            }

        }
        if (log.isDebugEnabled()) {
            log.debug("response auth message:" + JsonUtil.toJson(response));
        }
        deviceMessageService.add(msg);
        ctx.writeAndFlush(response);
    }

}