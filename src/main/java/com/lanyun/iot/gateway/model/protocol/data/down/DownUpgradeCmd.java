package com.lanyun.iot.gateway.model.protocol.data.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 设备升级命令Dto
 */
@Data
@ToString
public class DownUpgradeCmd {
    /**
     * 老版本
     */
    @JsonProperty("oVer")
    private String oVer;
    /**
     * 新版本
     */
    @JsonProperty("nVer")
    private String nVer;
    /**
     * 升级服务器IP
     */
    private String upgradeIp;
    /**
     * 升级服务器断开
     */
    private Integer upgradePort;
}
