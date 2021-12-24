package com.lanyun.iot.gateway.model.exception;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 23:14 2018/6/26
 * @ Description：${description}
 * @ Modified By：
 */
public class IotSysException extends RuntimeException {
    public IotSysException() {
    }

    public IotSysException(String message) {
        super(message);
    }

    public IotSysException(String message, Throwable cause) {
        super(message, cause);
    }

    public IotSysException(Throwable cause) {
        super(cause);
    }

    public IotSysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
