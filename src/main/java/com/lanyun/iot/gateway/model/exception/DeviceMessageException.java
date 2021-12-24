package com.lanyun.iot.gateway.model.exception;

/**
 * 设备消息异常
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 21:25 2018/9/1
 * @ Description：${description}
 * @ Modified By：
 */
public class DeviceMessageException extends DeviceException {

    public DeviceMessageException() {
    }

    public DeviceMessageException(String message) {
        super(message);
    }

    public DeviceMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceMessageException(Throwable cause) {
        super(cause);
    }

    public DeviceMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
