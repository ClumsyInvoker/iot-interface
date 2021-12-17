package com.lanyun.datasource.protocol.data.up.v4;

import lombok.Data;
import lombok.ToString;

/**
 *  设备上传预警信息
 * @author wanghu
 * @date 2020-12-24
 */
@Data
@ToString
public class DeviceWarningReport {
    /**
     * 当前预警类型
     */
    private Integer warn;
    /**
     * 当前预警值
     */
    private Integer state;
}
