package com.lanyun.datasource.protocol.cmd;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * 设备消息实体类
 */
@Data
@ToString(callSuper = true)
public class DeviceMessage<DAT> implements IDeviceMessage {

    /**
     * 消息格式的版本号
     */
    Integer version;
    /**
     * 消息序列号
     */
    @JSONField(ordinal = 1)
    private String msgNo;
    /**
     * 设备号
     */
    @JSONField(ordinal = 2)
    private String machNo;
    /**
     * 命令，消息类型
     */
    @JSONField(ordinal = 3)
    private Integer cmd;
    /**
     * 时间戳
     */
    @JSONField(ordinal = 4)
    private Long time;
    /**
     * 数据字典
     */
    @JSONField(ordinal = 5)
    private DAT data;
}
