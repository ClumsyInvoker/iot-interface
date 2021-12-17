package com.lanyun.datasource.service;

import com.lanyun.datasource.entity.OriginalDeviceMessage;
import com.lanyun.datasource.repository.OriginalDeviceMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-27 16:40
 */
@Service
public class OriginalDeviceMessageService {

    @Autowired
    private OriginalDeviceMessageDao deviceMessageDao;

    public void insert(OriginalDeviceMessage deviceMessage) {
        deviceMessageDao.insert(deviceMessage);
    }
}
