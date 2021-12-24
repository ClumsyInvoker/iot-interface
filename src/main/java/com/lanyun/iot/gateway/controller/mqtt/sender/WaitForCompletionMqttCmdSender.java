package com.lanyun.iot.gateway.controller.mqtt.sender;

import com.lanyun.iot.gateway.service.handler.cmd.CmdSender;
import com.lanyun.iot.gateway.service.handler.cmd.InMemoryDeviceCmdStateHolder;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 22:44 2018/11/12
 * @ Description：${description}
 * @ Modified By：
 */
@Slf4j
@Component
public class WaitForCompletionMqttCmdSender implements CmdSender {

    @Resource
    private MqttCmdSender cmdSender;
    @Resource
    private InMemoryDeviceCmdStateHolder deviceCmdStateHolder;

    @Override
    public Integer send(String deviceTopicPrefix, String deviceSerialNo, DeviceMessage cmd) {
        int state = deviceCmdStateHolder.queryState(deviceSerialNo);
        //下发中，返回忙碌状态
        //先 command 掉
//        if (state == DeviceCmdStateHolder.STATE_IN_ISSUE)
//            return BUSY;
        //下发
        boolean locked = false;
        try {
            locked = deviceCmdStateHolder.tryLock(deviceSerialNo);
            if (!locked) //锁失败，直接失败
            {
                return BUSY;
            }
            deviceCmdStateHolder.beforeSend(deviceSerialNo);
            Integer result = cmdSender.send(deviceTopicPrefix, deviceSerialNo, cmd);
            if (result == null || result == SUCCESS) {
                deviceCmdStateHolder.cmdSuccess(deviceSerialNo);
                return SUCCESS;
            }
            return BUSY;
        } finally {
            if (locked) {
                deviceCmdStateHolder.unlock(deviceSerialNo);
            }
        }
    }
}
