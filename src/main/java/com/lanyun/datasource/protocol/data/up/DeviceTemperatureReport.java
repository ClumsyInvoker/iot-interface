package com.lanyun.datasource.protocol.data.up;

import lombok.Data;
import lombok.ToString;

/**
 * 设备温度上报Dto
 */
@Data
@ToString
public class DeviceTemperatureReport {
    //温度
    private Double temp;
}
