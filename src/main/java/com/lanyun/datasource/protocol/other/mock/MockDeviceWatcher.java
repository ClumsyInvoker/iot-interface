package com.lanyun.datasource.protocol.other.mock;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.InitializingBean;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 23:13 2018/7/6
 * @ Description：看门狗
 * @ Modified By：
 */
@Slf4j
public class MockDeviceWatcher implements InitializingBean {
    //conf
    /**
     * 检测的时间间隔，s
     */
    private int checkIntervalSeconds;
    //conf
    /**
     * 报警间隔
     */
    private long alarmSpanSeconds;
    //conf
    /**
     * 是否启用
     */
    private boolean enable;

    private String mockDeviceId;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getCheckIntervalSeconds() {
        return checkIntervalSeconds;
    }

    public void setCheckIntervalSeconds(int checkIntervalSeconds) {
        this.checkIntervalSeconds = checkIntervalSeconds;
    }

    public long getAlarmSpanSeconds() {
        return alarmSpanSeconds;
    }

    public void setAlarmSpanSeconds(long alarmSpanSeconds) {
        this.alarmSpanSeconds = alarmSpanSeconds;
    }

    public String getMockDeviceId() {
        return mockDeviceId;
    }

    public void setMockDeviceId(String mockDeviceId) {
        this.mockDeviceId = mockDeviceId;
    }
    //////////////////////////////////////////////////

    private volatile long lastReceive;
    private Timer timer;

    public boolean isMockDeviceMsg(String topic, MqttMessage mqttMessage)
    {
        if (topic.endsWith(mockDeviceId))
        {
            mockDeviceMsgReceived();
            String data = new String(mqttMessage.getPayload());
            log.info("receive mockDevice msg: " + data);
            return true;
        }
        return false;
    }

    private void mockDeviceMsgReceived()
    {
        this.lastReceive = System.currentTimeMillis();
    }

    private void alarm()
    {
        log.error(String.format("超过[%s]秒未收到模拟设备的消息", alarmSpanSeconds));
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            long current = System.currentTimeMillis();
            if(current - lastReceive > alarmSpanSeconds * 1000)
            {
                alarm();
            }
        }
    };

    @Override
    public void afterPropertiesSet() throws Exception {
        if (enable)
        {
            timer = new Timer(true);
            timer.scheduleAtFixedRate(timerTask, 30 * 1000, checkIntervalSeconds * 1000);
        }
    }
}
