package com.lanyun.datasource.protocol.netty.codec.binary;

import com.alibaba.fastjson.JSON;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 二进制消息解析
 */
@Slf4j
public class DeviceMessageEncoder extends MessageToByteEncoder<DeviceMessage> {

    private static final int MAX_LEN = 0xffff;

    @Override
    protected void encode(ChannelHandlerContext ctx, DeviceMessage msg, ByteBuf out) throws Exception {
        String json = JSON.toJSONString(msg);
        byte[] payload = json.getBytes(StandardCharsets.UTF_8);
        if (payload.length > MAX_LEN) {
            throw new IllegalStateException("消息过长，len:"+ payload.length);
        }
        int len = (payload.length + 1) & 0xffff;
        out.writeShort(len);
        //版本号
        out.writeByte(1);
        out.writeBytes(payload);
    }
}
