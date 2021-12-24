package com.lanyun.iot.gateway.service;

import com.lanyun.iot.gateway.model.entity.MqttSendMessage;
import com.lanyun.iot.gateway.DAO.MqttSendMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-09-29 20:36
 */
@Service
public class MqttSendMessageService {

    @Autowired
    private MqttSendMessageDao mqttSendMessageDao;

    public void insert(MqttSendMessage mqttSendMessage) {
        mqttSendMessageDao.insert(mqttSendMessage);
    }
}
