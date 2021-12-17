package com.lanyun.datasource.protocol.data.up.v4;

import lombok.Data;
import lombok.ToString;

/**
 *  设备更新二维码内容
 * @author wanghu
 * @date 2020-12-24
 */
@Data
@ToString
public class UpdateQrCode {
    /**
     * 二维码内容
     */
    private String msg;
}
