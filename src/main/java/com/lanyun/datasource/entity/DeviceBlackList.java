package com.lanyun.datasource.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-28 15:25
 */
@Document(collection = "black_list")
@Data
public class DeviceBlackList {
    @Id
    private String id;
    /**
     * 设备号
     */
    @Field("machNo")
    private String machNo;
    /**
     * 原因
     */
    private String reason;
    /**
     * 来源
     */
    private Integer source;
    /**
     * 接受时间
     */
    @Field("createTime")
    private Long createTime;
}
