package com.lanyun.iot.gateway.model.protocol.data.up;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 设备基础信息Dto
 */
@Data
@ToString
public class DeviceInfoReport {
    /**
     * 设备CCID号 - 物联网卡管理相关
     */
    @JsonProperty("CCID")
    private String ccid;
    /**
     * 设备地址，基站地址GPS
     */
    private String loc;
    /**
     * Hard Version 硬件版本
     */
    private String hver;
    /**
     * Soft Version 软件版本
     */
    private String sver;
}
