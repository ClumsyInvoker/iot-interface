package com.lanyun.iot.gateway.controller.routers;

import com.lanyun.iot.gateway.model.exception.DeviceException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-09-18 14:15
 */
@Slf4j
public class BaseRouters {

    protected void overrideFields(Field[] fields, String url) {
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String name = field.getName();
                if ("hostConfig".equals(name)) {
                    continue;
                }
                Object o = field.get(this);
                if (o instanceof String) {
                    field.set(this, url + o);
                    log.info((String) field.get(this));
                }
            } catch (IllegalAccessException e) {
                log.error("初始化微服务路由失败", e);
                throw new DeviceException("初始化微服务路由失败");
            }
        }
    }
}
