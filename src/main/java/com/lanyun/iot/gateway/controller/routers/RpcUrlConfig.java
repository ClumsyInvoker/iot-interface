package com.lanyun.iot.gateway.controller.routers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-09-29 13:56
 */
@Data
@Component
public class RpcUrlConfig {

    @Value("${lanyun.service.device.host}")
    private String deviceHost;

    @Value("${lanyun.service.device.port}")
    private Integer devicePort;

    private String deviceUrl;

    @PostConstruct
    public void init() {
        deviceUrl = devicePort == null ? deviceHost : deviceHost + ":" + devicePort;
    }
}
