package com.lanyun.datasource.repository;

import com.lanyun.datasource.entity.OriginalDeviceMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-27 16:39
 */
@Repository
public interface OriginalDeviceMessageDao extends MongoRepository<OriginalDeviceMessage, String> {
}
