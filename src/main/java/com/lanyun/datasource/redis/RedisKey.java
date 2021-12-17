package com.lanyun.datasource.redis;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 14:13 2018/7/22
 * @ Description：${description}
 * @ Modified By：
 */
public class RedisKey {

    /**
     * Set结构，保存设备在线、离线、故障黄铜
     */
    private static final String PREFIX_AREA_DEVICE = "irb_area_device_";

    private static final String PREFIX_AREA_COMPANY = "irb_area_company_";

    /**
     * Hash结构，保存每个设备的产废，处置，存量信息
     */
    private static final String PREFIX_STATISTIC = "irb_statistic_";

    /**
     * Hash结构，缓存每个设备发送的最后一条消息
     */
    private static final String KEY_DEVICE_MESSAGE = "irb_device_message";

    /**
     * 保存为Hash结构
     */
    private static final String KEY_ALL_DEVICE_INFO = "irb_device_all";
    private static final String KEY_ALL_COMPANY_INFO = "irb_company_all";
    private static final String KEY_ALL_FLOW_INFO = "irb_flow_all";

    /**
     * 所有设备列表的key, value为hash表，hashkey为serialNo
     * @return
     */
    public static String getAllDeviceInfoKey()
    {
        return KEY_ALL_DEVICE_INFO;
    }

    /**
     * 所有企业列表的key, value为hash表，hashkey为companyId
     * @return
     */
    public static String getAllCompanyInfoKey()
    {
        return KEY_ALL_COMPANY_INFO;
    }

    /**
     * 所有流转图列表的key, value为hash表，hashkey为产废企业companyId
     * @return
     */
    public static String getAllFlowInfoKey()
    {
        return KEY_ALL_FLOW_INFO;
    }



    /**
     * 地域设备集合
     * @param areaCode
     * @return
     */
    public static String getAreaDeviceKey(String areaCode)
    {
        return PREFIX_AREA_DEVICE + areaCode;
    }

    /**
     * 地域设备集合
     * @param areaCodes
     * @return
     */
    public static List<String> getAreaDeviceKey(List<String> areaCodes)
    {
        List<String> keys = new ArrayList<>(areaCodes.size());
        for (String areaCode : areaCodes)
        {
            String key = getAreaDeviceKey(areaCode);
            keys.add(key);
        }
        return keys;
    }

    /**
     * 地域公司集合
     * @param areaCode
     * @return
     */
    public static String getAreaCompanyKey(String areaCode)
    {
        return PREFIX_AREA_COMPANY + areaCode;
    }

    /**
     * 地域公司集合
     * @param areaCodes
     * @return
     */
    public static List<String> getAreaCompanyKey(List<String> areaCodes)
    {
        List<String> keys = new ArrayList<>(areaCodes.size());
        for (String areaCode : areaCodes)
        {
            String key = getAreaCompanyKey(areaCode);
            keys.add(key);
        }
        return keys;
    }


    /**
     * 消息缓存
     * @return
     */
    public static String getDeviceMessageKey()
    {
        return KEY_DEVICE_MESSAGE;
    }

    /**
     * 危废生产量的缓存key
     * @param dateKey
     * @return
     */
    public static String getStatisticProduceKey(String dateKey)
    {
        return PREFIX_STATISTIC + "produce_" + dateKey;
    }

    /**
     * 处置量
     * @param dateKey
     * @return
     */
    public static String getStatisticConsumeKey(String dateKey)
    {
        return PREFIX_STATISTIC + "consume_" + dateKey;
    }

}
