package com.lanyun.iot.gateway.model.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-27 15:02
 */
@Component
@Order(3)
public class InitIotSchedulerThreadPoolExecutorBean {

    @Bean
    public SchedulingTaskExecutor mqttMessageThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(200);
        executor.setThreadNamePrefix("iot_mqtt_handler_");
        executor.setDaemon(true);
        return executor;
    }

}
