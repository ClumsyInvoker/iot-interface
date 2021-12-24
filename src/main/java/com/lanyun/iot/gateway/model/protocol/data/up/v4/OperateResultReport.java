package com.lanyun.iot.gateway.model.protocol.data.up.v4;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 设备上传日志信息
 * @author wanghu
 * @date 2020-12-24
 */
@Data
@ToString
public class OperateResultReport {
    /**
     * 进入污染物类型
     */
    private Integer in;
    /**
     * 排出污染物类型
     */
    private Integer out;
    /**
     * 本次操作流量计流量差计算的重量
     * 若污染物为含油污水，这里则为weight1，若污染物是生活污水，这里则为weight2，若污染物是废矿物油，这里则为weight3
     */
    private BigDecimal weight;
    /**
     * 本次进或出污染物流量计起始读数和结束读数的差值
     */
    private BigDecimal flow;
    /**
     * 扫码开门用户id
     */
    private Integer userId;
}
