package com.lanyun.datasource.protocol.netty;

import com.lanyun.datasource.protocol.netty.codec.binary.DeviceBinaryMsgEncoder;
import com.lanyun.datasource.protocol.netty.codec.binary.DeviceMessageDecoder;
import com.lanyun.datasource.protocol.netty.codec.binary.DeviceMessageEncoder;
import com.lanyun.datasource.protocol.netty.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 初始化netty服务过滤器
 */
public class IotDeviceServerHandlerInitializer extends
        ChannelInitializer<SocketChannel> {

    // 长度字段默认2字节
    private int lengthFieldLen = 2;
    // 默认十分钟超时，断开连接
    private int idleTimeoutSeconds = 10 * 60;
    // 消息长度限制
    private int maxFrameLength = 1024 * 64;

    public IotDeviceServerHandlerInitializer(int lengthFieldLen, int idleTimeoutSeconds) {
        this.lengthFieldLen = lengthFieldLen;
        this.idleTimeoutSeconds = idleTimeoutSeconds;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
         //all
        pipeline.addLast("idleStateHandler",new IdleStateHandler(idleTimeoutSeconds, 0, 0));
        pipeline.addLast("channelStateHandler", new ChannelStatusHandler());
         //inBound
        pipeline.addLast("lengthFrameDecoder", new LengthFieldBasedFrameDecoder(maxFrameLength, 0, lengthFieldLen, 0, 2));
        pipeline.addLast("deviceMessageDecoder", new DeviceMessageDecoder());
        //outBound
        pipeline.addLast("deviceMessageEncoder", new DeviceMessageEncoder());
        pipeline.addLast("deviceBinaryMsgEncoder", new DeviceBinaryMsgEncoder());
        //inBound
        pipeline.addLast("deviceAuthHandler",new NettyAuthMessageHandler());
        pipeline.addLast("deviceUpgradeHandler",new DeviceUpgradeHandler());
        pipeline.addLast("noHandledMsgHandler", new NoHandledMessageHandler());
    }
}