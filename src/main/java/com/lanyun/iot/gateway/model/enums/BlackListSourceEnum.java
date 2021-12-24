package com.lanyun.iot.gateway.model.enums;

import lombok.Getter;

/**
 * 黑名单来源
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-28 16:40
 */
@Getter
public enum BlackListSourceEnum implements BaseCodeEnum<Integer, String> {

    /**
     *
     */
    AUTO(1, "自动"),
    ADMIN(2, "后台添加"),
    ;

    private Integer code;

    private String message;

    BlackListSourceEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
