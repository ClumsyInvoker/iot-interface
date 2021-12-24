package com.lanyun.iot.gateway.controller.mqtt.cmd;

import com.lanyun.iot.gateway.model.protocol.data.down.AuthResponse;
import com.lanyun.iot.gateway.model.protocol.data.down.DownParamSettingCmd;
import com.lanyun.iot.gateway.model.protocol.data.up.DeviceErrorReport;
import com.lanyun.iot.gateway.model.protocol.data.up.DeviceInfoReport;
import com.lanyun.iot.gateway.model.protocol.data.up.DeviceParamReport;
import com.lanyun.iot.gateway.model.protocol.data.up.v4.*;
import lombok.Getter;
/**
 * VERSION = 4对应的命令码
 * @author wanghu
 * @date 2020-12-23
 */

@Getter
public enum Version_4_CmdEnum {

    UP_AUTH(1110, null, "认证请求"),
    DOWN_AUTH_RESPONSE(1210, AuthResponse.class, "认证响应"),
    UP_DEVICE_INFO(3110, DeviceInfoReport.class, "设备信息上报"),
    UP_DEVICE_ERROR(3131, DeviceErrorReport.class, "设备故障上报"),
    UP_DEVICE_PARAM(3150, DeviceParamReport.class, "设备参数上报"),
    // 单个参数下发
    DOWN_PARAM_SET(3251, DownParamSettingCmd.class, "下发参数配置"),
    UP_PARAM_SET_RESPONSE(3151, null, "下发参数配置响应"),
    UP_TIME_VALIDATE_REQUEST(3154, null, "设备向平台上报消息校准时间请求"),
    UP_TIME_VALIDATE_RESPONSE(3254, null, "设备向平台上报消息校准时间响应"),
    // 一次下发一个远程控制指令
    DOWN_REMOTE_CONTROL(3290, DownParamSettingCmd.class, "平台向设备下传远程控制指令"),
    DOWN_REMOTE_CONTROL_RESPONSE(3190, null, "平台向设备下传远程控制指令"),
    UP_LEVEL_WEIGHT_INFO(3191, LevelWeightReport.class, "设备上传设备液位重量信息"),
    UP_STATUS_INFO(3198, StatusInfoReport.class, "设备上传设备传感器信息"),
    // 一次只会上报单个预警
    UP_ALARM_MESSAGE(3192, DeviceWarningReport.class, "设备上传预警信息"),
    // 鱼舱通过何种方式向平台请求更新二维码？
    UPDATE_QRCODE(3293, UpdateQrCode.class, "设备更新二维码"),
    UPDATE_QRCODE_RESPONSE(3193, null, "设备更新二维码响应"),
    //
    DOWN_OPERATOR_INFO(3294, null, "平台向设备下发当前操作者信息"),
    DOWN_OPERATOR_INFO_RESPONSE(3194, null, "平台向设备下发当前操作者信息响应"),
    // 已经有device_message，日志表是否还有必要？
    UP_DEVICE_LOG(3195, DeviceLogReport.class, "设备上传日志信息"),
    UP_OPERATE_RESULT(3196, OperateResultReport.class, "设备上传本次进或出污染物结束总信息"),
    UP_SEPARATE_RESULT(3197, SeparateResultReport.class, "设备上传本次油水分离器预处理量"),
    UP_DEVICE_DISCONNECT(3140, null, "设备断开连接"),
    DEVICE_APPLY_QRCODE(3199, null, "设备向平台服务端请求二维码数据刷新屏幕二维码"),
    ;

    private int cmd;
    private Class<?> clazz;
    private String desc;

    Version_4_CmdEnum(int cmd, Class<?> clazz, String desc) {
        this.cmd = cmd;
        this.clazz = clazz;
        this.desc = desc;
    }

    public static Class<?> getCmdClass(int cmd) {
        return getCmdRequired(cmd).getClazz();
    }

    public static Version_4_CmdEnum getCmdRequired(int cmd) {
        Version_4_CmdEnum item = getCmd(cmd);
        if (item == null)
            throw new IllegalArgumentException("unknown cmd: " + cmd);
        return item;
    }

    public static Version_4_CmdEnum getCmd(int cmd) {
        for (Version_4_CmdEnum item : values()) {
            if (item.cmd == cmd) {
                return item;
            }
        }
        return null;
    }
}
