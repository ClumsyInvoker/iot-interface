package com.lanyun.datasource.routers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;

/**
 * 海油设备认证路由
 * @date 2020-12-25
 * @author wanghu
 */
@Data
@Slf4j
@Component
public class DeviceVersion4Router extends BaseRouters{

    @Autowired
    private RpcVersion4UrlConfig rpcVersion4UrlConfig;

    /**
     * 设备权限认证
     */
    private String mqttDeviceAuth = "/mqtt/device/auth";

    /**
     * 设备消息
     */
    private String deviceMessageAdd = "/devices/message";
    /**
     * 设备升级信息
     */
    private String deviceUpgradeInfo = "/devices/%s/upgrade";

    @PostConstruct
    public void init() {
        Field[] declaredFields = DeviceVersion4Router.class.getDeclaredFields();
        overrideFields(declaredFields, rpcVersion4UrlConfig.getOceanUrl());
    }
}
