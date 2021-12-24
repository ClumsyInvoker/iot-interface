package com.lanyun.iot.gateway.utils;

import com.lanyun.iot.gateway.model.enums.BaseCodeEnum;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-06-15 14:19
 */
public class EnumUtil {

    public static <T extends BaseCodeEnum> T getByCode(Object code, Class<T> enumClass) {
        for (T value : enumClass.getEnumConstants()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }

    public static <D extends BaseCodeEnum> D getByMessage(Object message, Class<D> enumClass) {
        for (D value : enumClass.getEnumConstants()) {
            if (message.equals(value.getMessage())) {
                return value;
            }
        }
        return null;
    }
}
