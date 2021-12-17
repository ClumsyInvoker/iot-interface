package com.lanyun.datasource.enums;

import lombok.Getter;
/**
 * 消息版本号枚举类
 * @Date 2020-12-23
 * @author wanghu
 */

@Getter
public enum MessageVersionEnum {

    /**
     * 机械/海油1.0协议消息版本号
     */
    MESSAGE_VERSION_ENUM_1(1, "jixie"),
    /**
     * 汽修协议消息版本号
     */
    MESSAGE_VERSION_ENUM_3(3, "qixiu"),
    /**
     * 海油2协议消息版本号
     */
    MESSAGE_VERSION_ENUM_4(4, "hy2"),
    ;

    private Integer code;

    private String message;

    MessageVersionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static MessageVersionEnum getItem(Integer code) {
        for (MessageVersionEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
