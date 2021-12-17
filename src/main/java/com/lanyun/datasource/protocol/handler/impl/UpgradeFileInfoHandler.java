package com.lanyun.datasource.protocol.handler.impl;

import com.lanyun.datasource.dto.DeviceUpgradeVersionDto;
import com.lanyun.datasource.protocol.handler.DeviceBinaryMessageHandlerFactory;
import com.lanyun.datasource.protocol.handler.file.DiskFileRetriever;
import com.lanyun.datasource.protocol.cmd.DeviceCmdEnum;
import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import com.lanyun.datasource.protocol.data.upgrade.DeviceBinaryMsg;
import com.lanyun.datasource.protocol.data.upgrade.DownFileInfoResponse;
import com.lanyun.datasource.routers.DeviceRouter;
import com.lanyun.datasource.routers.DeviceVersion4Router;
import com.lanyun.datasource.utils.BinaryUtil;
import com.lanyun.datasource.utils.HttpUtil;
import com.lanyun.datasource.utils.UpgreadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 1120
 * 请求获取升级包信息处理器
 */
@Component
@Slf4j
public class UpgradeFileInfoHandler extends AbstractBinaryMessageHandler {

    @Autowired
    private DiskFileRetriever fileRetriever;

    @Autowired
    private DeviceRouter deviceRouter;

    @Autowired
    private DeviceVersion4Router version4Router;

    @Override
    @PostConstruct
    public void init() {
        DeviceBinaryMessageHandlerFactory.registry(this);
    }

    @Override
    public boolean support(DeviceBinaryMsg input) {
        return input != null && DeviceCmdEnum.UP_UPGRADE_FILE_INFO_REQ.getCmd() == input.getCmd();
    }

    @Override
    protected boolean doFilter(DeviceMessage deviceMessage) {
        return false;
    }

    @Override
    public DeviceBinaryMsg process(DeviceBinaryMsg input) {
        log.info("收到升级文件请求: " + BinaryUtil.toHexString(input.getAllData()));
        // 设备升级 - 通用蕴藏改款请求海油2平台
        String url = "";
        if (input.getSerialNo().startsWith("U11010000")){
            url = version4Router.getDeviceUpgradeInfo();
        }else {
            url = deviceRouter.getDeviceUpgradeInfo();
        }
        DeviceUpgradeVersionDto version = HttpUtil.get(String.format(url, input.getSerialNo()), DeviceUpgradeVersionDto.class);
        if (version == null) {
            log.error("获取升级信息失败");
            return null;
        }

        byte[] data = fileRetriever.getFileContentByUrl(version.getFile());
        int len = data.length;
        short splitterNum = UpgreadFileUtil.getSplitterNum(data);

        DownFileInfoResponse response = new DownFileInfoResponse();
        response.setAllow(DownFileInfoResponse.ALLOW_UPGRADE);
        response.setDate(version.getFileDate());
        int deviceType = Integer.parseInt(version.getDeviceType(), 16);
        response.setType((byte) deviceType);
        response.setHardVersion(version.getHardVersion());
        response.setSoftVersion(version.getSoftVersion());
        response.setFileLen(len);
        response.setSplitNum(splitterNum);
        response.setCrc16(BinaryUtil.calcCRC16(data));
        byte[] respData = response.writeToByteArray();
        DeviceBinaryMsg outMsg = new DeviceBinaryMsg();
        outMsg.setSerialNo(input.getV1SerialNo());
        outMsg.setCmd((short) DeviceCmdEnum.DOWN_UPGRADE_FILE_INFO_RESPONSE.getCmd());
        outMsg.setData(respData);
        return outMsg;
    }

}
