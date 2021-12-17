package com.lanyun.datasource.filter;

import com.lanyun.datasource.protocol.cmd.DeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WasteCollectFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${lanyun.device.wastedevicekey}")
    private String wastedevicekey;

    public boolean doFilter(DeviceMessage deviceMessage) {
        if (redisTemplate == null) {
            log.error("redis disconnect");
            return false;
        }
        Boolean filter = redisTemplate.opsForSet().isMember(wastedevicekey, deviceMessage.getMachNo());
        return filter;
    }
}
