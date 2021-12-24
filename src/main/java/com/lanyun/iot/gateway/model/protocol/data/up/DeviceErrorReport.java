package com.lanyun.iot.gateway.model.protocol.data.up;

import lombok.Data;
import lombok.ToString;

/**
 * 设备故障Dto
 */
@Data
@ToString
public class DeviceErrorReport {
    /**
     * 产生故障
     */
    public static final Integer ERROR_OCCUR = 1;
    /**
     * 故障消除
     */
    public static final Integer ERROR_CLEAR = 0;
    /**
     * 告警类型,告警码
     */
    private String err;
    /**
     * 告警状态，1代表告警，0代表清除告警
     */
    private Integer state;
}
