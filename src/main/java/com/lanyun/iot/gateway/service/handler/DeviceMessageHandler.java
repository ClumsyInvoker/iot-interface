package com.lanyun.iot.gateway.service.handler;


/**
 * 抽象处理器接口
 * 处理原则：
 * 1、所有收到的设备消息都落库
 * 2、危废重量变化类消息数量庞大，暂存如Redis，经统计器处理后直接落库为已处理状态
 * 3、其他类型消息，直接以Init落库，然后统计器从数据库中扫描取出，再做统计
 */
public interface DeviceMessageHandler<MSG> {

    void init();

    MSG handle(MSG input);

    boolean support(MSG input);

    MSG process(MSG input);
}
