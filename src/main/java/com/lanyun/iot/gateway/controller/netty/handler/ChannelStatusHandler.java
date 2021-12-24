package com.lanyun.iot.gateway.controller.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 16:53 2018/6/17
 * @ Description：${description}
 * @ Modified By：
 */
@Slf4j
public class ChannelStatusHandler extends ChannelInboundHandlerAdapter {


    /**
     * 超时无心跳事件
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state()== IdleState.READER_IDLE){
                log.info("channel idle timeout, close channel " + ctx.channel().remoteAddress());
                ctx.close();
            }
        }else {
            super.userEventTriggered(ctx,evt);
        }
    }
}
