package com.lanyun.datasource.service;

import com.alibaba.fastjson.JSON;
import com.lanyun.datasource.dto.device.DeviceMessageEntity;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import com.lanyun.datasource.protocol.data.upgrade.DeviceBinaryMsg;
import com.lanyun.datasource.routers.DeviceRouter;
import com.lanyun.datasource.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-10-17 19:36
 */
@Service
@Slf4j
public class DeviceMessageService {

    @Autowired
    private DeviceRouter deviceRouter;

    public void add(DeviceBinaryMsg input) {
        DeviceMessageEntity entity = new DeviceMessageEntity();
        entity.setSerialNo(input.getSerialNo());
        entity.setCmd((int) input.getCmd());
        entity.setMessage(new String(input.getData()));
        entity.setMessageStatus("done");
        HttpUtil.postFromJson(deviceRouter.getDeviceMessageAdd(), entity, String.class);
    }

    public void add(DeviceMessage input) {
        DeviceMessageEntity entity = new DeviceMessageEntity();
        entity.setSerialNo(input.getMachNo());
        entity.setCmd(input.getCmd());
        entity.setMessage(JSON.toJSONString(input.getData()));
        entity.setMessageStatus("done");
        HttpUtil.postFromJson(deviceRouter.getDeviceMessageAdd(), entity, String.class);
    }
}
