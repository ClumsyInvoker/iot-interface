package com.lanyun.iot.gateway.controller.filter;

import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-28 14:42
 */
public interface DeviceMessageFilter {

    void init();

    boolean doFilter(DeviceMessage deviceMessage);
}
