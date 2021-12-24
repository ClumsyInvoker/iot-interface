package com.lanyun.iot.gateway.controller.mqtt.cmd;

import lombok.Getter;

/**
 * 设备参数配置枚举
 *
 * 待优化-没鸟用
 */
@Getter
public enum DeviceParamConfigEnum {

    /**
     *
     */
    CFZL_1("39", "通道一重量上限", "CFZL-1"),
    CFZL_2("40", "通道二重量上限", "CFZL-2"),
    CFZL_3("41", "通道三重量上限", "CFZL-3"),
    CFZL_4("42", "通道四重量上限", "CFZL-4"),
    CFZL_5("43", "通道五重量上限", "CFZL-5"),
    CFZL_6("44", "通道六重量上限", "CFZL-6"),
    CFZL_7("45", "通道七重量上限", "CFZL-7"),
    CFZL_8("46", "通道八重量上限", "CFZL-8");

    private String key;
    private String desc;
    private String wasteType;

    DeviceParamConfigEnum(String key, String desc, String wasteType) {
        this.key = key;
        this.desc = desc;
        this.wasteType = wasteType;
    }

    public static DeviceParamConfigEnum getParamKeyByWasteType(String wasteType) {
        DeviceParamConfigEnum[] values = DeviceParamConfigEnum.values();
        for (DeviceParamConfigEnum config : values) {
            if (config.getWasteType().compareTo(wasteType) == 0) {
                return config;
            }
        }
        return null;
    }
}
