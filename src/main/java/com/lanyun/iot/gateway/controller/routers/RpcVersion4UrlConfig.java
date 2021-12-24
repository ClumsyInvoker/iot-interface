package com.lanyun.iot.gateway.controller.routers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 海油设备认证路由
 * @date 2020-12-25
 * @author wanghu
 */
@Data
@Component
public class RpcVersion4UrlConfig {

    @Value("${lanyun.service.ocean.host}")
    private String oceanHost;

    @Value("${lanyun.service.ocean.port}")
    private Integer oceanPort;

    private String oceanUrl;

    @PostConstruct
    public void init() {
        oceanUrl = oceanPort == null ? oceanHost : oceanHost + ":" + oceanPort;
    }
}
