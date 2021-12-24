package com.lanyun.iot.gateway.model.protocol.data.up;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.LinkedHashMap;

/**
 * 设备重量预警Dto
 */
@Data
@ToString
public class DeviceAlarmReport extends LinkedHashMap<String,DeviceAlarmReport.AlarmInfo> {

    public static final Integer ALARM_OCCUR = 1;
    public static final Integer ALARM_CLEAR = 0;

    /**
     * 重量信息
     */
    @Data
    public static class AlarmInfo {
        /**
         * 预警时的重量
         */
        @JsonProperty("W")
        private Double W;
        /**
         * 预警状态
         */
        @JsonProperty("S")
        private Integer S;
    }
}
