package com.lanyun.iot.gateway.model.enums;

import lombok.Getter;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-12-25 10:57
 */
@Getter
public enum TopicEnum implements BaseCodeEnum<String, String> {

    /**
     * 机械相关
     */
    JIXIE_DEVICE("irb/device/", ""),
    JIXIE_DATA("irb/device/data", ""),
    JIXIE_STATE("irb/device/state", ""),
    /**
     * 海油1指定设备相关，淘汰后可以删除
     */
    HAIYOU_DEVICE("irb/device/haiyou/", ""),
    HAIYOU_DATA("irb/device/haiyou/data", ""),
    HAIYOU_STATE("irb/device/haiyou/state", ""),
    /**
     * 海油2相关
     */
    OCEAN_DEVICE("irb/device/ocean/", ""),// 此条topic长度有限制
    OCEAN_DEVICE_1("irb/device/4/", ""),
    OCEAN_DATA("irb/device/ocean/data", ""),
    OCEAN_STATE("irb/device/ocean/state", ""),
    /**
     * 江苏危废相关
     */
    WASTE_DEVICE("irb/device/waste/", ""),// 此条topic长度有限制 - 待修复
    WASTE_DATA("irb/device/waste/data", ""),
    WASTE_STATE("irb/device/waste/state", "")
    ;

    private String code;

    private String message;

    TopicEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
