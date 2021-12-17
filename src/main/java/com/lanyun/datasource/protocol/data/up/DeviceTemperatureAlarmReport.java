package com.lanyun.datasource.protocol.data.up;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 设备温度预警Dto
 */
@Data
@ToString
public class DeviceTemperatureAlarmReport {
    /**
     * 发生温度预警
     */
    public static final Integer ALARM_OCCUR = 1;
    /**
     * 温度预警清除
     */
    public static final Integer ALARM_CLEAR = 0;
    /**
     * 预警时的温度
     */
    @JsonProperty("T")
    private Double T;
    /**
     * 预警状态
     */
    @JsonProperty("S")
    private Integer S;
}
