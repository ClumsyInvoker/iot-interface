package com.lanyun.iot.gateway.service.handler.impl;

import com.lanyun.iot.gateway.controller.filter.BlackListFilter;
import com.lanyun.iot.gateway.controller.filter.DeviceMessageFilter;
import com.lanyun.iot.gateway.service.handler.DeviceMessageHandler;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象消息处理器
 */
public abstract class AbstractMessageHandler implements DeviceMessageHandler<DeviceMessage> {

    private List<DeviceMessageFilter> filterList = new ArrayList<>();

    @Autowired
    private BlackListFilter blackListFilter;

    protected void initialize() {
        filterList.add(blackListFilter);
    }

    protected void addInterceptor(DeviceMessageFilter filter) {
        filterList.add(filter);
    }

    protected List<DeviceMessageFilter> getInterceptor() {
        return filterList;
    }

    protected abstract boolean doFilter(DeviceMessage deviceMessage);

    private boolean filter(DeviceMessage deviceMessage) {
        boolean filter;
        for (DeviceMessageFilter deviceMessageFilter : filterList) {
            filter = deviceMessageFilter.doFilter(deviceMessage);
            if (filter) {
                return filter;
            }
        }
        filter = doFilter(deviceMessage);
        return filter;
    }

    /**
     *
     * @param input
     * @return null表示filter验证没过
     */
    @Override
    public DeviceMessage handle(DeviceMessage input) {
        if (filter(input)) {
            return null;
        }
        return input;
    }

    @Override
    public DeviceMessage process(DeviceMessage input) {
        return null;
    }
}
