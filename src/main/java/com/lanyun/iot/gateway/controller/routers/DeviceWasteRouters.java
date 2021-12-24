package com.lanyun.iot.gateway.controller.routers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
@Data
@Slf4j
@Component
public class DeviceWasteRouters extends BaseRouters{

    @Autowired
    private RpcWasteUrlConfig rpcWasteUrlConfig;

    /**
     * 设备权限认证
     */
    private String mqttDeviceAuth = "/mqtt/device/auth";

    /**
     * 设备升级信息
     */
    private String deviceUpgradeInfo = "/devices/%s/upgrade";

    /**
     * 设备消息
     */
    private String deviceMessageAdd = "/devices/message";

    @PostConstruct
    public void init() {
        Field[] declaredFields = DeviceWasteRouters.class.getDeclaredFields();
        overrideFields(declaredFields, rpcWasteUrlConfig.getWasteUrl());
    }
}
