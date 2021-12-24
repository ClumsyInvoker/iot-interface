package com.lanyun.iot.gateway.model.protocol.data.up.v4;

import lombok.Data;
import lombok.ToString;

/**
 * 设备上传日志信息
 * @author wanghu
 * @date 2020-12-24
 */
@Data
@ToString
public class DeviceLogReport {
    /**
     * 当前日志类型
     */
    private Integer journal;
    /**
     * 当前日志值
     */
    private Integer action;
}
