package com.lanyun.iot.gateway.controller.filter;

import com.alibaba.fastjson.JSON;
import com.lanyun.iot.gateway.controller.mqtt.cmd.DeviceMessage;
import com.lanyun.iot.gateway.service.DeviceBlackListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-28 15:23
 */
@Component
@Slf4j
public class BlackListFilter implements DeviceMessageFilter {

    @Autowired
    private DeviceBlackListService deviceBlackListService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${lanyun.device.blackKey}")
    private String blackKey;

    @PostConstruct
    @Override
    public void init() {
        Set<String> blackDeviceList = deviceBlackListService.getAllBlackList();
        stringRedisTemplate.delete(blackKey);
        if (!CollectionUtils.isEmpty(blackDeviceList)) {
            String[] blackArr = new String[blackDeviceList.size()];
            blackDeviceList.toArray(blackArr);
            stringRedisTemplate.opsForSet().add(blackKey, blackArr);
        }
    }

    @Override
    public boolean doFilter(DeviceMessage deviceMessage) {
        if (stringRedisTemplate == null) {
            log.error("redis disconnect");
            return false;
        }
        Boolean filter = stringRedisTemplate.opsForSet().isMember(blackKey, deviceMessage.getMachNo());
        if (filter != null && filter) {
            log.info("存在黑名单中:" + JSON.toJSONString(deviceMessage));
            return true;
        }
        return false;
    }
}
