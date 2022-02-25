package com.lanyun.iot.gateway.controller.mqtt.cmd;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IotMessage implements Serializable {
    
    private Long id;
    private Long ts;
    private Long deviceId;
    private String method;
    private String topic;
    private String data;

    public static IotMessage decode(@NotNull String payload) {
        return JSON.parseObject(payload, IotMessage.class);
    }

}
