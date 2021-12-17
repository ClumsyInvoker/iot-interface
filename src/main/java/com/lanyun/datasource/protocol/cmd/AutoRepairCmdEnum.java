package com.lanyun.datasource.protocol.cmd;

import com.lanyun.datasource.protocol.data.down.AuthResponse;
import com.lanyun.datasource.protocol.data.down.DownParamSettingCmd;
import com.lanyun.datasource.protocol.data.down.DownRebootCmd;
import com.lanyun.datasource.protocol.data.down.DownUpgradeCmd;
import com.lanyun.datasource.protocol.data.up.*;
import lombok.Getter;
/**
 * 汽修设备CMD枚举类
 * VERSION = 3
 */
@Getter
public enum AutoRepairCmdEnum {

    /**
     * 认证消息
     */
    UP_AUTH(1110, null, "认证请求"),
    DOWN_AUTH_RESPONSE(1210, AuthResponse.class, "认证响应"),
    /**
     * 设备上报消息
     */
    UP_DEVICE_INFO(3110, DeviceInfoReport.class, "设备信息上报"),
    /**
     * 3170:
     * 危废重量变化上报,
     * 包含重量增加和小幅减少两种情况（只取Y值）。该上报只用于设备的临时重量展示，不参与台账统计.
     */
    UP_DEVICE_WEIGHT_CHANGED_DATA(3170, DeviceDataReport.class, "设备重量变化上报"),
    /**
     * 3172:每天的定时上报（只取Y值），
     * Y值为 remain, 配合3171的 consume, 计算出当天的 produce.
     */
    UP_DEVICE_WEIGHT_SCHEDULED_DATA(3172, DeviceDataReport.class, "设备重量定时上报"),
    /**
     * 设备上传预警信息
     */
    UP_DEVICE_ALARM(3130, DeviceAlarmReport.class, "设备预警上报"),
    /**
     * 上报故障
     */
    UP_DEVICE_ERROR(3131, QXDeviceErrorReport.class, "设备故障上报"),
    /**
     * 设备断开消息
     */
    UP_DEVICE_DISCONNECT(3140, null, "设备断开连接"),
    /**
     * 设备参数上报消息
     */
    UP_DEVICE_PARAM(3150, DeviceParamReport.class, "设备参数上报"),
    /**
     * 下发配置(平台服务端向设备下发配置参数)
     */
    DOWN_PARAM_SET(3251, DownParamSettingCmd.class, "下发参数配置"),
    /**
     * 消息3251的响应
     */
    UP_PARAM_SET_RESPONSE(3151, null, "下发参数配置响应"),
    /**
     * 下发升级命令(平台服务端向设备下发升级命令)
     */
    DOWN_UPGRADE(3252, DownUpgradeCmd.class, "下发升级命令"),
    /**
     * 消息3252的响应
     */
    UP_UPGRADE_RESPONSE(3152, null, "下发升级命令响应消息"),
    /**
     * 下发重启命令(平台服务端向设备下发重启命令)
     */
    DOWN_REBOOT(3253, DownRebootCmd.class, "下发重启命令"),
    /**
     * 消息3153的响应
     */
    UP_REBOOT_RESPONSE(3153, null, "下发重启命令响应消息"),
    /**
     * 3180
     * 电量预警上报
     */
    UP_DEVICE_VOLTAGE_ALARM(3180, DeviceVoltageAlarmReport.class, "设备电量预警上报"),

    /**
     * 3181
     * 电量上报
     */
    UP_DEVICE_VOLTAGE(3181, DeviceVoltageReport.class, "设备电量上报"),
    ;

    private int cmd;
    private Class<?> clazz;
    private String desc;

    AutoRepairCmdEnum(int cmd, Class<?> clazz, String desc) {
        this.cmd = cmd;
        this.clazz = clazz;
        this.desc = desc;
    }

    public static Class<?> getCmdClass(int cmd) {
        return getCmdRequired(cmd).getClazz();
    }

    public static AutoRepairCmdEnum getCmd(int cmd) {
        for (AutoRepairCmdEnum item : values()) {
            if (item.cmd == cmd) {
                return item;
            }
        }
        return null;
    }

    public static AutoRepairCmdEnum getCmdRequired(int cmd) {
        AutoRepairCmdEnum item = getCmd(cmd);
        if (item == null)
            throw new IllegalArgumentException("unknown cmd: " + cmd);
        return item;
    }
}
