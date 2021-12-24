package com.lanyun.iot.gateway.model.protocol.data.upgrade;

import com.lanyun.iot.gateway.utils.BinaryUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备升级 - 请求分片升级文件Dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpFileSpliterRequest {
    //请求第K个分片
    private short spliterNum;

    public static UpFileSpliterRequest read(byte[] data) {
        short num = BinaryUtil.toShort(data);
        return new UpFileSpliterRequest(num);
    }

    public static void main(String[] args) {
        byte[] data = new byte[2];
        data[0] = 0;
        data[1] = (byte) 0x80;
        short num = BinaryUtil.toShort(data);
        System.out.println(num);
    }

}
