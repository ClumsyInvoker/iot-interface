package com.lanyun.datasource.protocol.handler.cmd;

import com.lanyun.datasource.protocol.cmd.DeviceMessage;

/**
 * 下发MQTT消息接口
 */
public interface CmdSender {

    int SUCCESS = 0; //成功
    int FAILED = -1; //失败
    int BUSY = 1; //忙
    int CACHED = 2; //已缓存，等待下发

    Integer send(String deviceTopicPrefix, String deviceSerialNo, DeviceMessage cmd);
}
