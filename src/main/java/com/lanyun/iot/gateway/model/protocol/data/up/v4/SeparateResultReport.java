package com.lanyun.iot.gateway.model.protocol.data.up.v4;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 设备上传本次油水分离器预处理量
 * @author wanghu
 * @date 2020-12-24
 */
@Data
@ToString
public class SeparateResultReport {
    /**
     * 含油污水罐当前液位
     */
    private BigDecimal level;
    /**
     * 油水分离器从启动到结束的时间，单位秒
     */
    private Integer time;
}
