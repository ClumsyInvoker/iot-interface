package com.lanyun.datasource.dto.black;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-28 16:32
 */
@Data
public class AddBlackList {

    @ApiModelProperty("设备序列号")
    @NotEmpty(message = "序列号不能为空")
    private String serialNo;

    @ApiModelProperty("添加原因")
    @NotEmpty(message = "原因")
    private String reason;
}
