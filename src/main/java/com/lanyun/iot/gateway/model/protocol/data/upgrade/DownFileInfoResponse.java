package com.lanyun.iot.gateway.model.protocol.data.upgrade;

import com.lanyun.iot.gateway.utils.BinaryUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * 设备升级-下载升级文件响应
 */
@Data
public class DownFileInfoResponse {

    public static final byte ALLOW_UPGRADE = 0x00;
    public static final byte DENY_UPGRADE = 0x01;
    //是否允许升级，1字节
    private byte allow;
    //日期，年/月/日， 10字节
    private String date;
    //机型编号， 1字节
    private byte type;
    //硬件版本,12字节
    private String hardVersion;
    //软件版本 12字节
    private String softVersion;
    //文件大小
    private int fileLen;
    //分片数量
    private short splitNum;
    //文件CRC校验
    private byte[] crc16;

    public byte[] writeToByteArray() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(allow);
        buf.writeBytes(BinaryUtil.getBytesRequiredLength(date, 10));
        buf.writeByte(type);
        buf.writeBytes(BinaryUtil.getBytesRequiredLength(hardVersion, 12));
        buf.writeBytes(BinaryUtil.getBytesRequiredLength(softVersion, 12));
        buf.writeInt(fileLen);
        buf.writeShort(splitNum);
        buf.writeBytes(crc16);
        //
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        return data;
    }


}
