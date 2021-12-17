package com.lanyun.datasource.repository;

import com.lanyun.datasource.entity.MqttSendMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-09-29 20:36
 */
@Repository
public interface MqttSendMessageDao extends MongoRepository<MqttSendMessage, String> {
}
