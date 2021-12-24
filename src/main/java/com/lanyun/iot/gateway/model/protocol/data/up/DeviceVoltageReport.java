package com.lanyun.iot.gateway.model.protocol.data.up;

import lombok.Data;
import lombok.ToString;

/**
 * 设备电量上报Dto
 */
@Data
@ToString
public class DeviceVoltageReport {
    //电量
    private Double voltage;
}
