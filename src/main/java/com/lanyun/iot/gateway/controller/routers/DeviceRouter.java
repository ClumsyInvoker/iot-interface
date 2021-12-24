package com.lanyun.iot.gateway.controller.routers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-09-18 14:17
 */
@Data
@Slf4j
@Component
public class DeviceRouter extends BaseRouters {

    @Autowired
    private RpcUrlConfig rpcUrlConfig;

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
        Field[] declaredFields = DeviceRouter.class.getDeclaredFields();
        overrideFields(declaredFields, rpcUrlConfig.getDeviceUrl());
    }

}
