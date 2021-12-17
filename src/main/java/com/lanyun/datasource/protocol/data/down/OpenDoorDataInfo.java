package com.lanyun.datasource.protocol.data.down;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 椒江两台设备下发扫码开门指令
 * U040119110001，U040119110002
 * 历史遗留问题，受限于硬件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenDoorDataInfo {

    /**
     * 门状态
     */
    private Integer OPdoor;
    /**
     * 用户id
     */
    private Integer userId;

}
