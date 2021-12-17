package com.lanyun.datasource.protocol.cmd;

import com.lanyun.datasource.protocol.data.down.OpenDoorDataInfo;
import com.lanyun.datasource.protocol.data.upgrade.DownFileInfoResponse;
import com.lanyun.datasource.protocol.data.upgrade.DownFileSpliterResponse;
import com.lanyun.datasource.protocol.data.upgrade.UpFileInfoRequest;
import com.lanyun.datasource.protocol.data.upgrade.UpFileSpliterRequest;
import com.lanyun.datasource.protocol.data.down.*;
import com.lanyun.datasource.protocol.data.up.*;
import lombok.Getter;

/**
 * 机械设备命令枚举类
 * VERSION = 1
 */
@Getter
public enum DeviceCmdEnum {
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
     * deprecated.
     * 下面3120, 3121, 3129, 3122 废弃不用.
     *
     * UP_DEVICE_LAUNCH_DATA(3120, DeviceDataReport.class, "危废投放数据（增加）上报"),
     * UP_DEVICE_TRANSPORT_DATA(3121, DeviceDataReport.class, "危废运输数据（减少）上报"),
     * UP_DEVICE_UNKNOWN_DATA(3129, DeviceDataReport.class, "未知原因重量变化上报"),
     * UP_DEVICE_LEAK_DATA(3122, DeviceDataReport.class, "设备泄漏（减少）时数据上报"),
     */
    /***********************************************************/
    /**
     * 20190319通讯协议变动:
     * 添加 3170,3171,3172命令码
     */
    /**
     * 3170:
     * 危废重量变化上报,
     * 包含重量增加和小幅减少两种情况（只取Y值）。该上报只用于设备的临时重量展示，不参与台账统计.
     */
    UP_DEVICE_WEIGHT_CHANGED_DATA(3170, DeviceDataReport.class, "设备重量变化上报"),

    /**
     * 3171:危废重量减少上报,
     * 用于正常的重量减少，实际的触发生成联单操作，参与台账统计.
     */
    UP_DEVICE_WEIGHT_REDUCED_DATA(3171, DeviceDataReport.class, "设备重量减少上报"),
    /**
     * 3172:每天的定时上报（只取Y值），
     * 用于台账统计,
     * Y值为 remain, 配合3171的 consume, 计算出当天的 produce.
     */
    UP_DEVICE_WEIGHT_SCHEDULED_DATA(3172, DeviceDataReport.class, "设备重量定时上报"),

    /**
     * 定时上报重量的响应, 用于硬件时间校准
     */
    DOWN_TIME_SYNCHRONIZE(3272, DownTimeSynchronizeCmd.class, "设备重量定时上报响应"),

    /***********************************************************/

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

    UP_DEVICE_ALARM(3130, DeviceAlarmReport.class, "设备预警上报"),
    UP_DEVICE_ERROR(3131, DeviceErrorReport.class, "设备故障上报"),
    UP_DEVICE_TEMPERATURE_ALARM(3132, DeviceTemperatureAlarmReport.class, "设备温度预警上报"),
    /**
     * 设备参数上报
     */
    UP_DEVICE_PARAM(3150, DeviceParamReport.class, "设备参数上报"),
    /**
     * 设备温度上报
     */
    UP_DEVICE_TEMP(3160, DeviceTemperatureReport.class, "设备温度上报"),
    /**
     * 设备断开消息
     */
    UP_DEVICE_DISCONNECT(3140, null, "设备断开连接"),

    /**
     * 下发配置
     */
    DOWN_PARAM_SET(3251, DownParamSettingCmd.class, "下发参数配置"),
    UP_PARAM_SET_RESPONSE(3151, null, "下发参数配置响应"),
    /**
     * 下发升级命令
     */
    DOWN_UPGRADE(3252, DownUpgradeCmd.class, "下发升级命令"),
    UP_UPGRADE_RESPONSE(3152, null, "下发升级命令响应消息"),
    /**
     * 下发重启命令
     */
    DOWN_REBOOT(3253, DownRebootCmd.class, "下发重启命令"),
    UP_REBOOT_RESPONSE(3153, null, "下发重启命令响应消息"),
    /**
     * 升级包下载
     */
    UP_UPGRADE_FILE_INFO_REQ(1120, UpFileInfoRequest.class, "请求获取升级包信息"),
    DOWN_UPGRADE_FILE_INFO_RESPONSE(1220, DownFileInfoResponse.class, "设备下载升级包信息响应消息"),
    UP_UPGRADE_FILE_REQ(1121, UpFileSpliterRequest.class, "请求升级包内容"),
    DOWN_UPGRADE_FILE_RESPONSE(1221, DownFileSpliterResponse.class, "响应对应的升级包内容"),
    UP_UPGRADE_RESULT_REQ(1122, null, "上报升级包结果"),
    /**
     * 请求清运和重量上报
     */
    TRANSPORT_APPLY(3156, DeviceTransportApply.class, "请求清运"),
    /**
     * 通用云仓参数设置
     */
    UNIVERSAL_STORAGE_PARAM_SET(3257, null, "通用云仓参数设置"),
    /**
     * 通用云仓参数设置响应
     */
    UNIVERSAL_STORAGE_PARAM_SET_RESPONSE(3157, null, "通用云仓参数设置响应"),
    UP_TIME_VALIDATE_REQUEST(3154, null, "设备向平台上报消息校准时间请求"),
    UP_TIME_VALIDATE_RESPONSE(3254, null, "设备向平台上报消息校准时间响应"),

    /**
     * U040119110001
     */
    RUNTIME_INTERVAL(80, null, "设备信息实时上报"),
    OPEN_DOOR_ANSWER(81, null, "开门响应"),
    CLOSE_DOOR(82, null, "关门"),
    HAIYOU_OPEN_DOOR(83, OpenDoorDataInfo.class, "开门"),
    ;

    private int cmd;
    private Class<?> clazz;
    private String desc;

    DeviceCmdEnum(int cmd, Class<?> clazz, String desc) {
        this.cmd = cmd;
        this.clazz = clazz;
        this.desc = desc;
    }

    public static Class<?> getCmdClass(int cmd) {
        return getCmdRequired(cmd).getClazz();
    }

    public static DeviceCmdEnum getCmd(int cmd) {
        for (DeviceCmdEnum item : values()) {
            if (item.cmd == cmd) {
                return item;
            }
        }
        return null;
    }

    public static DeviceCmdEnum getCmdRequired(int cmd) {
        DeviceCmdEnum item = getCmd(cmd);
        if (item == null)
            throw new IllegalArgumentException("unknown cmd: " + cmd);
        return item;
    }
}
