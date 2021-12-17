package com.lanyun.datasource.exception;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 20:00 2018/9/23
 * @ Description：${description}
 * @ Modified By：
 */
public class IllegalDeviceParamException extends IllegalArgumentException {

    public IllegalDeviceParamException() {
    }

    public IllegalDeviceParamException(String s) {
        super(s);
    }

    public IllegalDeviceParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalDeviceParamException(Throwable cause) {
        super(cause);
    }
}
