package com.lanyun.iot.gateway.proxy.redis.manage;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 16:22 2018/7/22
 * @ Description：用作一次消息去重
 * @ Modified By：
 */
public interface MqttMessageCache {

    void saveToCache(String key, byte[] data);

    byte[] getFromCache(String key);

    boolean exists(String key);

    void remove(String key);
}
