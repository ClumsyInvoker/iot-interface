package com.lanyun.datasource.dto.device;


import lombok.Data;

import java.util.Date;

/**
 * 设备消息实体类
 */
@Data
public class DeviceMessageEntity {

    private Integer id;
    /**
     * 设备序列号
     */
    private String serialNo;
    /**
     * 命令码
     */
    private Integer cmd;
    /**
     * 企业id
     */
    private String companyId;
    /**
     * 消息序列号
     */
    private String msgNo;
    /**
     * 消息报文
     */
    private String message;
    /**
     * 消息状态
     */
    private String messageStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
