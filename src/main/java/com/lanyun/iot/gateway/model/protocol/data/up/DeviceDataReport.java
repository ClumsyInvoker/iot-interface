package com.lanyun.iot.gateway.model.protocol.data.up;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.LinkedHashMap;

/**
 * 设备重量上报Dto
 * {
 * 	"data": {
 * 		"U010901": {
 * 			"X": 13.0,
 * 			"Y": 13.0
 *                },
 * 		"U010802": {
 * 			"X": 0.0,
 * 			"Y": 0.0
 *        }
 *      }
 *  }
 */
@Data
@ToString
public class DeviceDataReport extends LinkedHashMap<String, DeviceDataReport.WeightInfo> {

    /**
     * 重量信息
     */
    @Data
    public static class WeightInfo {
        /**
         * 变化前的重量
         */
        @JsonProperty("X")
        private Double X;
        /**
         * 变化后的重量
         */
        @JsonProperty("Y")
        private Double Y;
    }
}
