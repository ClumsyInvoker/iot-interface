package com.lanyun.iot.gateway.service.handler.cmd;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 19:42 2018/11/11
 * @ Description：${description}
 * @ Modified By：
 */
@Slf4j
@Component
public class InMemoryDeviceCmdStateHolder implements DeviceCmdStateHolder {

    private Map<String, CmdState> map = new ConcurrentHashMap<>();
    private Map<String,AtomicBoolean> lockMap = new ConcurrentHashMap<>();

    @Override
    public int queryState(String serialNo) {
        if (StringUtils.isBlank(serialNo))
            return STATE_IDLE;
        CmdState state = map.get(serialNo);
        if (state == null)
            return STATE_IDLE;
        //
        if (System.currentTimeMillis() - state.getTime() > TIME_OUT) {
            map.remove(serialNo);
            return STATE_ISSUE_TIMEOUT;
        } else {
            return STATE_IN_ISSUE;
        }
    }

    @Override
    public boolean tryLock(String serialNo) {
        AtomicBoolean lock = lockMap.computeIfAbsent(serialNo, k->new AtomicBoolean(Boolean.FALSE));
        return lock.compareAndSet(Boolean.FALSE,Boolean.TRUE);
    }

    @Override
    public void unlock(String serialNo) {
        AtomicBoolean lock = lockMap.computeIfAbsent(serialNo, k->new AtomicBoolean(Boolean.FALSE));
        lock.set(Boolean.FALSE);
    }

    @Override
    public void beforeSend(String serialNo) {
        CmdState state = map.computeIfAbsent(serialNo, CmdState::buildEmpty);
        state.setTime(System.currentTimeMillis());
    }

    @Override
    public void cmdSuccess(String serialNo) {
        map.remove(serialNo);
    }

    @Override
    public void cmdFailed(String serialNo) {
        map.remove(serialNo);
    }
}
