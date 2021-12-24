package com.lanyun.iot.gateway.controller.netty.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 14:23 2018/7/15
 * @ Description：${description}
 * @ Modified By：
 */
@Slf4j
public class NoHandledMessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.error("no handled tcp message: " + JSON.toJSONString(msg));
    }
}
