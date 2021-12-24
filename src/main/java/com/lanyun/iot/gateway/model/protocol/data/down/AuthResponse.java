package com.lanyun.iot.gateway.model.protocol.data.down;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 设备认证响应
 */
@Data
@ToString
public class AuthResponse {

    /**
     * 认证：0认证通过，1：认证失败
     */
    @JSONField(ordinal = 1)
    private Integer state;
    /**
     * 返回域名
     */
    @JSONField(ordinal = 2)
    private String ip;
    /**
     * 1883,端口号
     */
    @JSONField(ordinal = 3)
    private Integer port;
    /**
     * 用户名
     * 这个字段序列号json后会变成全小写，加个注解纠正以下
     */
    @JSONField(ordinal = 8)
    @JsonProperty("uName")
    private String uName;
    /**
     * 连接MQTT服务器的密码
     */
    @JSONField(ordinal = 4)
    private String passwd;
    /**
     * 客户端发布消息的topic
     */
    @JSONField(ordinal = 5)
    private String publishTopic;
    /**
     * 客户端订阅的topic
     */
    @JSONField(ordinal = 6)
    private String subscribeTopic;
    /**
     * will 遗嘱的topic
     */
    @JSONField(ordinal = 7)
    private String deviceStateTopic;
}
