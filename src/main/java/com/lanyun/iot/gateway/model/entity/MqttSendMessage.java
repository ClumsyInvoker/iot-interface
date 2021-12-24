package com.lanyun.iot.gateway.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-09-29 20:33
 */
@Document(collection = "mqtt_send_message")
@Data
public class MqttSendMessage {

    @Id
    private String id;
    /**
     * 版本
     */
    private Integer version;
    /**
     * 消息序列号
     */
    @Field("msgNo")
    private String msgNo;
    /**
     * 设备号
     */
    @Field("machNo")
    private String machNo;
    /**
     * 命令，消息类型
     */
    private Integer cmd;
    /**
     * 时间戳
     */
    private Long time;
    /**
     * 数据字典
     */
    private String data;
    /**
     * 接受时间
     */
    @Field("createTime")
    private Long createTime;
}
