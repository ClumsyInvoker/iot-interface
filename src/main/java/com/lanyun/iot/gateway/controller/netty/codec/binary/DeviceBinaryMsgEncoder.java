package com.lanyun.iot.gateway.controller.netty.codec.binary;

import com.lanyun.iot.gateway.model.protocol.data.upgrade.DeviceBinaryMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息转为二进制报文
 */
@Slf4j
public class DeviceBinaryMsgEncoder extends MessageToByteEncoder<DeviceBinaryMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, DeviceBinaryMsg msg, ByteBuf out) throws Exception {
        byte[] allData = msg.prepareToSend();
        out.writeShort(DeviceBinaryMsg.LEN_VERSION + allData.length);
        out.writeByte(DeviceBinaryMsg.VERSION);
        out.writeBytes(allData);
    }
}
