package com.lanyun.datasource.protocol.netty.codec.binary;

import com.lanyun.datasource.constant.Constant;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import com.lanyun.datasource.protocol.netty.codec.json.JsonMessageDecoder;
import com.lanyun.datasource.protocol.netty.codec.json.MessageDecoder;
import com.lanyun.datasource.protocol.data.upgrade.DeviceBinaryMsg;
import com.lanyun.datasource.utils.BinaryUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * netty解析设备消息
 */
@Slf4j
public class DeviceMessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    private MessageDecoder<DeviceMessage> messageDecoder = new JsonMessageDecoder();
    private static final List<Integer> versions = new ArrayList<>(Arrays.asList(
            Constant.DEVICE_VERSION_1,
            Constant.DEVICE_VERSION_3,
            Constant.DEVICE_VERSION_4));

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        Integer version = Integer.valueOf(msg.readByte());
        if (!versions.contains(version)) {
            log.error("收到netty消息版本号不支持，version=" + version);
        }
        boolean binaryMsg = isBinaryMessage(msg);
        if (binaryMsg) {
            DeviceBinaryMsg deviceBinaryMsg = parseBinaryMessage(msg);
            if (deviceBinaryMsg == null) {
                return;
            }
            out.add(deviceBinaryMsg);
        } else {
            byte[] payload = new byte[msg.readableBytes()];
            msg.readBytes(payload);
            DeviceMessage deviceMessage = parseToDeviceMessage(payload);
            if (deviceMessage != null) {
                out.add(deviceMessage);
            }
        }
    }

    /**
     * 判断是否是设备升级消息
     *
     * @return
     */
    private boolean isBinaryMessage(ByteBuf msg) {
        if (msg.readableBytes() < 3) {
            return false;
        }
        int currentIndex = msg.readerIndex();
        int len = msg.readableBytes();
        byte[] msgHead = new byte[2];
        msg.getBytes(currentIndex, msgHead);
        byte msgTail = msg.getByte(currentIndex + len - 1);
        return BinaryUtil.equals(DeviceBinaryMsg.HEAD_2_BYTE, msgHead) && DeviceBinaryMsg.TAIL_1_BYTE == msgTail;
    }

    /**
     * 解析为字符串消息
     *
     * @param payload
     * @return
     */
    private DeviceMessage parseToDeviceMessage(byte[] payload) {
        DeviceMessage deviceMessage = messageDecoder.decode(payload);
        return deviceMessage;
    }

    /**
     * 解析为二进制消息
     *
     * @return
     */
    private DeviceBinaryMsg parseBinaryMessage(ByteBuf msg) {
        DeviceBinaryMsg deviceBinaryMsg = new DeviceBinaryMsg();
        //
        int length = msg.readableBytes();
        byte[] all = new byte[length];
        msg.readBytes(all);
        //
        deviceBinaryMsg.setAllData(all);

        deviceBinaryMsg.receiveCheck();
        //done
        return deviceBinaryMsg;
    }

//    public static String hexStrToStr(String hexStr) {
//        String str = "0123456789ABCDEF";
//        char[] hexs = hexStr.toCharArray();
//        byte[] bytes = new byte[hexStr.length() / 2];
//        int n;
//        for (int i = 0; i < bytes.length; i++) {
//            n = str.indexOf(hexs[2 * i]) * 16;
//            n += str.indexOf(hexs[2 * i + 1]);
//            bytes[i] = (byte) (n & 0xff);
//        }
//        return new String(bytes);
//    }
}
