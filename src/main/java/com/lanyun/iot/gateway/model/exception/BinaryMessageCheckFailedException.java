package com.lanyun.iot.gateway.model.exception;

/**
 * 二进制消息检查失败异常
 * Create by fengzhaofeng on 2018/9/28
 */
public class BinaryMessageCheckFailedException extends DeviceMessageException {
    public BinaryMessageCheckFailedException() {
    }

    public BinaryMessageCheckFailedException(String message) {
        super(message);
    }

    public BinaryMessageCheckFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BinaryMessageCheckFailedException(Throwable cause) {
        super(cause);
    }

    public BinaryMessageCheckFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
