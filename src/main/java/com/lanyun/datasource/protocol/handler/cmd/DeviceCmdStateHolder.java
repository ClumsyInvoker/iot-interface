package com.lanyun.datasource.protocol.handler.cmd;

import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 19:41 2018/11/11
 * @ Description：保存命令下发状态，设备无返回情况下不允许下一条下发
 * @ Modified By：
 */
public interface DeviceCmdStateHolder {

    /**
     * 超时时间,60s
     */
    long TIME_OUT = 1000 * 60;
    /**
     * 空闲，可以下发命令
     */
    int STATE_IDLE = 1;
    /**
     * 下发中，设备还未明确返回，不允许下发新命令
     */
    int STATE_IN_ISSUE = 2;
    /**
     * 上一条下发命令超时未返回，允许再次下发
     */
    int STATE_ISSUE_TIMEOUT = 3;
    /**
     * 查询当前状态
     *
     * @param serialNo
     * @return
     */
    int queryState(String serialNo);
    /**
     * 下发前锁定，方并发
     * @param serialNo
     * @return
     */
    boolean tryLock(String serialNo);
    /**
     * 下发后释放锁
     * @param serialNo
     * @return
     */
    void unlock(String serialNo);
    /**
     * 发送前
     * @param serialNo
     */
    void beforeSend(String serialNo);
    /**
     * 参数下发成功
     * @param serialNo
     */
    void cmdSuccess(String serialNo);
    /**
     * 参数下发失败
     * @param serialNo
     */
    void cmdFailed(String serialNo);
    /**
     * 对命令状态的封装
     */
    @Data
    class CmdState implements Serializable{
        private String serialNo; //序列号
        private long time; //上一次下发时间
        private String lastCmdKey; //上一条命令key
        private Integer lastCmdValue; //上一条命令value
        //
        public static CmdState buildEmpty(String serialNo) {
            CmdState state = new CmdState();
            state.setSerialNo(serialNo);
            state.setTime(System.currentTimeMillis());
            return state;
        }
    }
}
