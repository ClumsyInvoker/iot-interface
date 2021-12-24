package com.lanyun.iot.gateway.model.protocol.data.up.v4;

import lombok.Data;
import lombok.ToString;

/**
 *  设备上传传感器信息
 * @author wanghu
 * @date 2020-12-24
 */
@Data
@ToString
public class StatusInfoReport {
    /**
     * 温湿度传感器温度，单位摄氏度
     */
    private String temperature1;
    /**
     * 油水分离器温度，单位摄氏度
     */
    private String temperature2;
    /**
     * 湿度，单位%
     */
    private String humidity;
    /**
     * 可燃气体浓度，单位%
     */
    private String gasPercentage;
    /**
     * 油分浓度，单位%
     */
    private String oilConcentration;
    /**
     * 含油污水泵压力传感器,0-0.4mpa
     */
    private String pressure1;
    /**
     * 生活污水泵压力传感器，0-0.4mpa
     */
    private String pressure2;
    /**
     * 废矿物油泵压力传感器，0-0.4mpa
     */
    private String pressure3;
    /**
     * 油水分离器压力传感器1，到时候设备到了确认是哪个，0-0.4mpa
     */
    private String pressure4;
    /**
     * 油水分离器压力传感器2，到时候设备到了确认是哪个，0-0.4mpa
     */
    private String pressure5;
    /**
     * 油水分离器压力传感器3，到时候设备到了确认是哪个，0-0.4mpa
     */
    private String pressure6;
}
