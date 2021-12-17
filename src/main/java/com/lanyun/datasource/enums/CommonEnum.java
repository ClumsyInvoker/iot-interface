package com.lanyun.datasource.enums;

import lombok.Getter;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-06-24 15:18
 */
@Getter
public enum CommonEnum implements BaseCodeEnum<Integer, String> {
    /**
     * 默认
     */
    DEFAULT(0, "否"),
    /**
     * 生效
     */
    YES(1, "是"),
    /**
     * 失效
     */
    NO(2, "否"),
    ;

    private Integer code;

    private String message;

    CommonEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

