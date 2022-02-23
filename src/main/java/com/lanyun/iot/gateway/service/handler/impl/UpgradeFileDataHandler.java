package com.lanyun.iot.gateway.service.handler.impl;

import com.lanyun.iot.gateway.model.dto.DeviceUpgradeVersionDto;
import com.lanyun.iot.gateway.service.handler.DeviceBinaryMessageHandlerFactory;
import com.lanyun.iot.gateway.service.handler.file.DiskFileRetriever;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceCmdEnum;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import com.lanyun.iot.gateway.model.protocol.data.upgrade.DeviceBinaryMsg;
import com.lanyun.iot.gateway.model.protocol.data.upgrade.DownFileSpliterResponse;
import com.lanyun.iot.gateway.model.protocol.data.upgrade.UpFileSpliterRequest;
import com.lanyun.iot.gateway.proxy.CloudWareHouseProxy;
import com.lanyun.iot.gateway.controller.routers.DeviceRouter;
import com.lanyun.iot.gateway.controller.routers.DeviceVersion4Router;
import com.lanyun.iot.gateway.utils.BinaryUtil;
import com.lanyun.iot.gateway.utils.HttpUtil;
import com.lanyun.iot.gateway.utils.UpgreadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 1121
 * 请求升级包内容处理器
 */
@Component
@Slf4j
public class UpgradeFileDataHandler extends AbstractBinaryMessageHandler {

    @Autowired
    private DiskFileRetriever fileRetriever;

    @Autowired
    private DeviceRouter deviceRouter;
    @Autowired
    private DeviceVersion4Router version4Router;

    @Autowired
    private CloudWareHouseProxy cloudWareHouseProxy;

    @Override
    @PostConstruct
    public void init() {
        DeviceBinaryMessageHandlerFactory.registry(this);
    }

    @Override
    public boolean support(DeviceBinaryMsg input) {
        return DeviceCmdEnum.UP_UPGRADE_FILE_REQ.getCmd() == input.getCmd();
    }

    @Override
    protected boolean doFilter(DeviceMessage deviceMessage) {
        return false;
    }

    @Override
    public DeviceBinaryMsg process(DeviceBinaryMsg input) {
        log.debug("收到请求升级文件请求: " + BinaryUtil.toHexString(input.getAllData()));
        // 设备升级 - 通用云仓改款请求海油2平台
        /*
        String url = "";
        if (input.getSerialNo().startsWith("U11010000")){
            url = version4Router.getDeviceUpgradeInfo();
        }else {
            url = deviceRouter.getDeviceUpgradeInfo();
        }
        DeviceUpgradeVersionDto version = HttpUtil.get(String.format(url, input.getSerialNo()), DeviceUpgradeVersionDto.class);
        */ 
        DeviceUpgradeVersionDto version = cloudWareHouseProxy.getDeviceUpgradeInfo(input.getSerialNo());
        if (version == null) {
            log.error("获取升级信息失败");
            return null;
        }

        byte[] file = fileRetriever.getFileContentByUrl(version.getFile());
        //
        byte[] data = input.getData();
        UpFileSpliterRequest request = UpFileSpliterRequest.read(data);

        short splitterNum = request.getSpliterNum();
        short totalNum = UpgreadFileUtil.getSplitterNum(file);
        if (splitterNum < 0 || splitterNum >= totalNum) {
            throw new IllegalArgumentException("illegal splitterNum " + splitterNum + " for total " + totalNum);
        }
        byte[] splitter = UpgreadFileUtil.getSplitter(file, splitterNum);
        splitter = UpgreadFileUtil.extendFF(splitter);


        DownFileSpliterResponse response = new DownFileSpliterResponse();
        response.setSpliterNum(request.getSpliterNum());
        response.setLength((short) splitter.length);
        response.setSpliterData(splitter);
        response.setXor(BinaryUtil.xor(splitter));

        byte[] respData = response.writeToByteArray();
        DeviceBinaryMsg outMsg = new DeviceBinaryMsg();
        outMsg.setSerialNo(input.getSerialNo());
        outMsg.setCmd((short) DeviceCmdEnum.DOWN_UPGRADE_FILE_RESPONSE.getCmd());
        outMsg.setData(respData);
        log.info("response " + splitterNum + " upgrade file slice for device " + input.getSerialNo());
        return outMsg;
    }
}
