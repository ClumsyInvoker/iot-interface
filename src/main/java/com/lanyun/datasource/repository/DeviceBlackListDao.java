package com.lanyun.datasource.repository;

import com.lanyun.datasource.entity.DeviceBlackList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-28 15:28
 */
@Repository
public interface DeviceBlackListDao extends MongoRepository<DeviceBlackList, String> {

    Page<DeviceBlackList> findAllByMachNo(String machNo, Pageable pageable);

    DeviceBlackList findByMachNo(String machNo);
}
