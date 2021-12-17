package com.lanyun.datasource.protocol.data.up;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 设备电量预警Dto
 */
@Data
@ToString
public class DeviceVoltageAlarmReport {
    /**
     * 发生电量预警
     */
    public static final Integer ALARM_OCCUR = 1;
    /**
     * 电量预警清除
     */
    public static final Integer ALARM_CLEAR = 0;
    /**
     * 预警时的电量
     */
    @JsonProperty("V")
    private Double V;
    /**
     * 预警状态
     */
    @JsonProperty("S")
    private Integer S;
}
