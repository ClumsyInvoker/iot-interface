package com.lanyun.datasource.exception;

/**
 * 设备异常
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 16:15 2018/6/17
 * @ Description：${description}
 * @ Modified By：
 */
public class DeviceException  extends IotSysException{
    public DeviceException() {
    }

    public DeviceException(String message) {
        super(message);
    }

    public DeviceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceException(Throwable cause) {
        super(cause);
    }

    public DeviceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
