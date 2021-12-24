package com.lanyun.iot.gateway.model.protocol.data.upgrade;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * 设备升级 - 下载分片升级文件响应
 */
@Data
public class DownFileSpliterResponse {

    //分片编号
    private short spliterNum;

    //分片长度
    private short length;

    //分片内容
    private byte[] spliterData;

    //数据内容异或值
    private byte xor;

    //
    public byte[] writeToByteArray()
    {
        ByteBuf buf = Unpooled.buffer();
        buf.writeShort(spliterNum);
        buf.writeShort(length);
        buf.writeByte(xor);
        buf.writeBytes(spliterData);
        //
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        return data;
    }
}
