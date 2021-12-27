package com.lanyun.iot.gateway.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="cloud-warehouse-service")
public interface CloudWareHouseProxy {

    @PostMapping("/service/iot-message")
    void handleIotMessage(@RequestParam("iot-message") String message);

}