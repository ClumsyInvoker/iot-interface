package com.lanyun.iot.gateway.model.protocol.data.up.v4;

import lombok.ToString;

/**
 * 设备上传液位重量信息
 *
 * @author wanghu
 * @date 2020-12-24
 */
@ToString
public class LevelWeightReport {
    /**
     * 含油污水罐内容物重量-单位Kg
     */
    private Integer weight1;
    /**
     * 生活污水罐内容物重量-单位Kg
     */
    private Integer weight2;
    /**
     * 废矿物油罐内容物重量-单位Kg
     */
    private Integer weight3;
    /**
     * 含油污水罐液位-重量计算液位
     */
    private Integer level11;
    /**
     * 含油污水罐液位-对比液位
     */
    private Integer level12;
    /**
     * 生活污水罐液位-重量计算液位
     */
    private Integer level21;
    /**
     * 生活污水罐液位-对比液位
     */
    private Integer level22;
    /**
     * 废矿物油罐液位-重量计算液位
     */
    private Integer level31;
    /**
     * 废矿物油罐液位-对比液位
     */
    private Integer level32;

    public Integer getWeight1() {
        if (weight1 == null) {
            weight1 = 0;
        }
        return weight1;
    }

    public void setWeight1(Integer weight1) {
        this.weight1 = weight1;
    }

    public Integer getWeight2() {
        if (weight2 == null) {
            weight2 = 0;
        }
        return weight2;
    }

    public void setWeight2(Integer weight2) {
        this.weight2 = weight2;
    }

    public Integer getWeight3() {
        if (weight3 == null) {
            weight3 = 0;
        }
        return weight3;
    }

    public void setWeight3(Integer weight3) {
        this.weight3 = weight3;
    }

    public Integer getLevel11() {
        if (level11 == null) {
            level11 = 0;
        }
        return level11;
    }

    public void setLevel11(Integer level11) {
        this.level11 = level11;
    }

    public Integer getLevel12() {
        if (level12 == null) {
            level12 = 0;
        }
        return level12;
    }

    public void setLevel12(Integer level12) {
        this.level12 = level12;
    }

    public Integer getLevel21() {
        if (level21 == null) {
            level21 = 0;
        }
        return level21;
    }

    public void setLevel21(Integer level21) {
        this.level21 = level21;
    }

    public Integer getLevel22() {
        if (level22 == null) {
            level22 = 0;
        }
        return level22;
    }

    public void setLevel22(Integer level22) {
        this.level22 = level22;
    }

    public Integer getLevel31() {
        if (level31 == null) {
            level31 = 0;
        }
        return level31;
    }

    public void setLevel31(Integer level31) {
        this.level31 = level31;
    }

    public Integer getLevel32() {
        if (level32 == null) {
            level32 = 0;
        }
        return level32;
    }

    public void setLevel32(Integer level32) {
        this.level32 = level32;
    }
}
