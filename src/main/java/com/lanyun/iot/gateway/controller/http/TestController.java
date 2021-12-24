package com.lanyun.iot.gateway.controller.http;

import com.lanyun.iot.gateway.model.protocol.data.upgrade.DeviceBinaryMsg;
import com.lanyun.iot.gateway.service.DeviceMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-10-17 19:49
 */
@RestController
public class TestController {

    @Autowired
    private DeviceMessageService deviceMessageService;

    @GetMapping("/test")
    public void test() {
        DeviceBinaryMsg msg = new DeviceBinaryMsg();
        msg.setAllData("hello world".getBytes());
        msg.setCmd((short)3172);
        msg.setSerialNo("U010418110018");
        msg.setData("data".getBytes());
        deviceMessageService.add(msg);
    }
}
