package com.lanyun.iot.gateway.proxy.redis.manage.impl;

import com.lanyun.iot.gateway.proxy.redis.RedisKey;
import com.lanyun.iot.gateway.proxy.redis.manage.MqttMessageCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 16:24 2018/7/22
 * @ Description：设备消息缓存，缓存每个设备发送的最后一条消息，用于去重
 * @ Modified By：
 */
@Slf4j
@Component
public class RedisMqttMessageCache implements MqttMessageCache {

    @Autowired
    private RedisTemplate redisTemplate;


    private volatile BoundHashOperations<String, String, String> boundHashOperations;

    private BoundHashOperations<String, String, String> operations()
    {
        if (boundHashOperations == null) {
            synchronized (RedisMqttMessageCache.class) {
                if (boundHashOperations == null) {
                    String key = RedisKey.getDeviceMessageKey();
                    boundHashOperations = redisTemplate.boundHashOps(key);
                }
            }
        }
        return boundHashOperations;
    }

    @Override
    public void saveToCache(String serialNo, byte[] payload) {
        if (payload == null || payload.length == 0)
            return;
        String value = Base64.getEncoder().encodeToString(payload);
        operations().put(serialNo, value);
    }

    @Override
    public byte[] getFromCache(String key) {
        String value = operations().get(key);
        if (StringUtils.isBlank(value))
            return null;
        return Base64.getDecoder().decode(value);
    }

    @Override
    public boolean exists(String key) {
        return getFromCache(key) != null;
    }

    @Override
    public void remove(String key) {
        operations().delete(key);
    }
}
