package com.lanyun.iot.gateway.model.dto;


import lombok.Data;
/**
 * 设备升级版本报文Dto-数据传输对象(Data Transfer Object)
 */
@Data
public class DeviceUpgradeVersionDto {

    private String file;

    private String fileDate;

    private String deviceType;

    private String hardVersion;

    private String softVersion;
}