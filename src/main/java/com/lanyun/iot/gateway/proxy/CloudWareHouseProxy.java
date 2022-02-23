package com.lanyun.iot.gateway.proxy;

import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import com.lanyun.iot.gateway.model.dto.DeviceUpgradeVersionDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="cloud-warehouse-service")
public interface CloudWareHouseProxy {

    @PostMapping("/devices/iot-message")
    void handleIotMessage(@RequestBody String message);

    @PostMapping("/mqtt/device/auth")
    DeviceMessage deviceAuth(@RequestBody DeviceMessage message);

    @GetMapping("/devices/{serialNo}/upgrade")
    DeviceUpgradeVersionDto getDeviceUpgradeInfo(@PathVariable("serialNo") String serialNo);

}