package com.lanyun.iot.gateway.model.protocol.data.upgrade;

import com.lanyun.iot.gateway.model.exception.BinaryMessageCheckFailedException;
import com.lanyun.iot.gateway.utils.BinaryUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Setter;

import java.util.Arrays;

/**
 * 设备升级-二进制消息
 */
@Setter
public class DeviceBinaryMsg {


    public static final int LEN_VERSION = 1;
    public static final int LEN_HEADER = 2;
    public static final int LEN_CMD = 2;
    public static final int LEN_SERIAL_NO = 7;
    public static final int LEN_CRC = 2;
    public static final int LEN_END = 1;


    public static final byte[] HEAD_2_BYTE = {0x55, 0x55};

    public static final byte TAIL_1_BYTE = (byte) 0xaa;

    public static final byte VERSION = 0x01;

    /////////////////////////////////////////
    // 偏移量
    ///////////////////////////////////////////
    //
    public static final int HEADER_START = 0;
    public static final int HEADER_END = 2;
    //
    public static final int SERIAL_START = 2;
    public static final int SERIAL_END = 9;
    //
    public static final int CMD_START = 9;
    public static final int CMD_END = 11;
    //
    public static final int DATA_START = 11;
    public static final int DATA_END_SUB = -3;
    //
    public static final int CRC_START_SUB = -3;
    public static final int CRC_END_SUB = -1;
    //
    public static final int TAIL_START_SUB = -1;
    public static final int TAIL_END_SUB = 0;
    /////////////////////////////////////////////////////////////////
    // 有用的信息就只有下面这些了
    ////////////////////////////////////////////////////////////////
    private byte[] allData;
    /////////////////////////////////////////////////////////////////
    //header
    private byte[] header;
    /**
     * 设备序列号，7 字节,发送时必传
     */
    private String serialNo;
    /**
     * 命令码，2 字节,发送时必传
     */
    private Short cmd;
    /**
     * 数据，n 字节,发送时必传
     */
    private byte[] data;
    /**
     * crc校验码
     */
    private byte[] crc;
    //end
    private Byte tail;

    public byte[] getHeader() {
        if (header == null) {
            header = BinaryUtil.subArray(allData, HEADER_START, HEADER_END);
        }
        return header;
    }

    public short getCmd() {
        if (cmd == null) {
            cmd = BinaryUtil.getShort(allData, CMD_START);
        }
        return cmd;
    }

    public String getSerialNo() {
        byte[] serialNoArray = BinaryUtil.subArray(allData, SERIAL_START, SERIAL_END);
        serialNo = BinaryUtil.parseNewBinarySerialNo(serialNoArray);
        return serialNo;
    }

    public String getV1SerialNo() {
        byte[] serialNoArray = BinaryUtil.subArray(allData, SERIAL_START, SERIAL_END);
        serialNo = BinaryUtil.parseOldBinarySerialNo(serialNoArray);
        return serialNo;
    }

    public byte[] getData() {
        if (data == null) {
            int length = allData.length;
            data = BinaryUtil.subArray(allData, DATA_START, DATA_END_SUB + length);
        }
        return data;
    }

    public byte[] getCrc() {
        if (crc == null) {
            int length = allData.length;
            crc = BinaryUtil.subArray(allData, CRC_START_SUB + length, CRC_END_SUB + length);
        }
        return crc;
    }

    public byte getTail() {
        if (tail == null) {
            tail = allData[TAIL_START_SUB + allData.length];
        }
        return tail;
    }

    public byte[] getAllData() {
        return allData;
    }


    public byte[] prepareToSend() {
        if (allData != null) {
            return allData;
        }
        //
        ByteBuf out = Unpooled.buffer();
        //version，version不作为crc校验
//        out.writeByte(DeviceBinaryMsg.VERSION);
        //header
        out.writeBytes(DeviceBinaryMsg.HEAD_2_BYTE);
        //serialNo
        byte[] serialNoArray = BinaryUtil.serialNoToByteArray(serialNo);
        out.writeBytes(serialNoArray);
        //cmd
        out.writeShort(cmd);
        //data
        out.writeBytes(data);
        //
        byte[] crcData = new byte[out.readableBytes()];
        out.readBytes(crcData);
        //crc16
        byte[] crc = BinaryUtil.calcCRC16(crcData);
        //
        int newLength = crcData.length + 2 + 1;
        byte[] all = Arrays.copyOf(crcData, newLength);
        all[newLength - 3] = crc[0];
        all[newLength - 2] = crc[1];
        all[newLength - 1] = TAIL_1_BYTE;
        this.allData = all;
        //
        return allData;
    }

    public void receiveCheck() {
        if (allData == null) {
            throw new BinaryMessageCheckFailedException("allData is null");
        }
        if (!BinaryUtil.equals(HEAD_2_BYTE, getHeader())) {
            throw new BinaryMessageCheckFailedException("error message header " + BinaryUtil.toHexString(getHeader()));
        }
        if (TAIL_1_BYTE != getTail()) {
            throw new BinaryMessageCheckFailedException("error tail byte " + getTail());
        }
        //
        byte[] crcData = BinaryUtil.subArray(allData, HEADER_START, CRC_START_SUB + allData.length);
        byte[] realCrc = BinaryUtil.calcCRC16(crcData);
        if (!BinaryUtil.equals(realCrc, getCrc())) {
            throw new BinaryMessageCheckFailedException(String.format("crc check failed, receive crc is %s, work out is %s", BinaryUtil.toHexString(getCrc()), BinaryUtil.toHexString(realCrc)));
        }
        //
    }
}
