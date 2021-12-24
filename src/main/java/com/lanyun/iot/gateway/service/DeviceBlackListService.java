package com.lanyun.iot.gateway.service;

import com.lanyun.iot.gateway.controller.advice.MyPage;
import com.lanyun.iot.gateway.model.dto.black.AddBlackList;
import com.lanyun.iot.gateway.model.entity.DeviceBlackList;
import com.lanyun.iot.gateway.model.enums.BlackListSourceEnum;
import com.lanyun.iot.gateway.model.exception.ProjectException;
import com.lanyun.iot.gateway.DAO.DeviceBlackListDao;
import com.lanyun.iot.gateway.utils.MyPageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-28 15:29
 */
@Service
@Slf4j
public class DeviceBlackListService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${lanyun.device.blackKey}")
    private String blackKey;

    @Autowired
    private DeviceBlackListDao deviceBlackListDao;

    public Set<String> getAllBlackList() {
        List<DeviceBlackList> blackLists = deviceBlackListDao.findAll();
        return blackLists.stream().map(DeviceBlackList::getMachNo).collect(Collectors.toSet());
    }

    @Transactional
    public void add(AddBlackList form) {
        DeviceBlackList deviceBlackList = deviceBlackListDao.findByMachNo(form.getSerialNo());
        if (deviceBlackList != null) {
            throw new ProjectException("已经存在");
        }
        DeviceBlackList record = new DeviceBlackList();
        record.setMachNo(form.getSerialNo());
        record.setReason(form.getReason());
        record.setSource(BlackListSourceEnum.ADMIN.getCode());
        record.setCreateTime(System.currentTimeMillis());
        try {
            deviceBlackListDao.insert(record);
            stringRedisTemplate.opsForSet().add(blackKey, form.getSerialNo());
        } catch (Exception e) {
            log.error("添加黑名单错误", e);
            throw new ProjectException(e.getMessage());
        }
    }

    public MyPage<DeviceBlackList> filter(String serialNo, Integer page, Integer size) {
        Page<DeviceBlackList> deviceBlackLists;
        if (StringUtils.isNotEmpty(serialNo)) {
            deviceBlackLists = deviceBlackListDao.findAllByMachNo(serialNo, PageRequest.of(page - 1, size));
        } else {
            deviceBlackLists = deviceBlackListDao.findAll(PageRequest.of(page - 1, size));
        }
        return MyPageUtil.pageToMyPage(deviceBlackLists);
    }

    @Transactional
    public void delete(String serialNo) {
        DeviceBlackList deviceBlackList = deviceBlackListDao.findByMachNo(serialNo);
        if (deviceBlackList == null) {
            throw new ProjectException("记录不存在");
        }
        try {
            deviceBlackListDao.delete(deviceBlackList);
            stringRedisTemplate.opsForSet().remove(blackKey, serialNo);
        } catch (Exception e) {
            log.error("删除黑名单失败", e);
            throw new ProjectException(e.getMessage());
        }
    }
}
