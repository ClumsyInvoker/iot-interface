package com.lanyun.iot.gateway.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="cloud-warehouse-service")
public interface CloudWareHouseProxy {

    @PostMapping("/devices/iot-message")
    void handleIotMessage(@RequestBody String message);

}