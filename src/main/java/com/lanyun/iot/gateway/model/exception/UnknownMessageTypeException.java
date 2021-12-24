package com.lanyun.iot.gateway.model.exception;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 15:46 2018/6/17
 * @ Description：${description}
 * @ Modified By：
 */
public class UnknownMessageTypeException extends DeviceException{
    public UnknownMessageTypeException() {
    }

    public UnknownMessageTypeException(String message) {
        super(message);
    }
}
