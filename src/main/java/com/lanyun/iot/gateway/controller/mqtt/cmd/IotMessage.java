package com.lanyun.iot.gateway.controller.mqtt.cmd;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * 设备消息新实体类
 */
@Data
@ToString(callSuper = true)
public class IotMessage implements IDeviceMessage {

    /**
     * 消息序列号
     */
    @JSONField(ordinal = 0)
    private Long id;
    /**
     * 时间戳
     */
    @JSONField(ordinal = 1)
    private Long ts;
    /**
     * 设备号
     */
    @JSONField(ordinal = 2)
    private String deviceId;
    /**
     * 消息方法
     */
    @JSONField(ordinal = 3)
    private String method;
    /**
     * 数据字典
     */
    @JSONField(ordinal = 4)
    private String data;
    /**
     * 消息主题
     */
    @JSONField(ordinal = 5)
    private String topic;

}
