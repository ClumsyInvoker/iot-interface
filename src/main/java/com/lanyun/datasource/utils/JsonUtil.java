package com.lanyun.datasource.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 21:34 2018/7/3
 * @ Description：${description}
 * @ Modified By：
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        // SerializationFeature for changing how JSON is written

        // pretty 格式化
        // mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 不序列化 null 字段
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 允许序列化空对象
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 日期字段转换为时间戳
        mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // DeserializationFeature for changing how JSON is read as POJOs:

        // 忽略未知字段
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // to allow coercion of JSON empty String ("") to null Object value:
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        //根据字段名序列化，不根据方法名
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    /**
     * 序列号
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        notNull(obj, JsonUtil.class.getName() + ".toJson param is null");
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JsonSerializationFailed", e);
        }
        return null;
    }

    /**
     * 发序列化
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> type) {
        notEmpty(json, JsonUtil.class.getName() + ".fromJson param [json] is null or empty");
        notNull(type, JsonUtil.class.getName() + ".fromJson param [type] is null");
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("JsonDeserializationFailed", e);
        }
        return null;
    }

    /**
     * 反序列化，支持泛型
     *
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        notEmpty(json, JsonUtil.class.getName() + ".fromJson param [json] is null or empty");
        notNull(typeReference, JsonUtil.class.getName() + ".fromJson param [typeReference] is null");
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            log.error("JsonDeserializationFailed", e);
        }
        return null;
    }

    public static <T> T convert(Object sourceObject, Class<T> targetType) {
        notNull(sourceObject, JsonUtil.class.getName() + ".convert param [sourceObject] is null");
        notNull(targetType, JsonUtil.class.getName() + ".convert param [targetType] is null");

        return mapper.convertValue(sourceObject, targetType);
    }

    public static <T> T convert(Object sourceObject, TypeReference<T> typeReference) {
        notNull(sourceObject, JsonUtil.class.getName() + ".convert param [sourceObject] is null");
        notNull(typeReference, JsonUtil.class.getName() + ".convert param [typeReference] is null");

        return mapper.convertValue(sourceObject, typeReference);
    }

    public static <T> T copy(Object sourceObject, Class<T> targetType)
    {
        notNull(sourceObject, JsonUtil.class.getName() + ".copy param [sourceObject] is null");
        notNull(targetType, JsonUtil.class.getName() + ".copy param [targetType] is null");

        String json = toJson(sourceObject);
        return fromJson(json, targetType);
    }
}
