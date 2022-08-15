package com.livk.common.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.util.*;

/**
 * <p>
 * JacksonUtils
 * </p>
 *
 * @author livk
 * @date 2022/8/15
 */
@UtilityClass
public class JacksonUtils {
    private static final ObjectMapper MAPPER = JsonMapper.builder().build();

    /**
     * json字符转Bean
     *
     * @param json  json string
     * @param clazz class
     * @param <T>   type
     * @return T
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String json, Class<T> clazz) {
        if (check(json, clazz)) {
            return null;
        }
        if (clazz.isInstance(json)) {
            return (T) json;
        }
        return MAPPER.readValue(json, clazz);

    }

    @SneakyThrows
    public static <T> T toBean(InputStream inputStream, Class<T> clazz) {
        return (inputStream == null || clazz == null) ? null :
                MAPPER.readValue(inputStream, clazz);
    }

    /**
     * 序列化
     *
     * @param obj obj
     * @return json
     */
    @SneakyThrows
    public static String toJsonStr(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        return MAPPER.writeValueAsString(obj);
    }

    /**
     * json to List
     *
     * @param json  json数组
     * @param clazz 类型
     * @param <T>   泛型
     * @return List<T>
     */
    @SneakyThrows
    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (check(json, clazz)) {
            return new ArrayList<>();
        }
        var collectionType = MAPPER.getTypeFactory()
                .constructCollectionType(List.class, clazz);
        return MAPPER.readValue(json, collectionType);
    }

    /**
     * json反序列化Map
     *
     * @param json       json字符串
     * @param keyClass   K Class
     * @param valueClass V Class
     * @return Map<K, V>
     */
    @SneakyThrows
    public static <K, V> Map<K, V> toMap(String json, Class<K> keyClass, Class<V> valueClass) {
        if (check(json, keyClass, valueClass)) {
            return Collections.emptyMap();
        }
        var mapType = MAPPER.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        return MAPPER.readValue(json, mapType);
    }

    @SneakyThrows
    public Properties toProperties(InputStream inputStream) {
        if (inputStream == null) {
            return new Properties();
        }
        var mapType = MAPPER.getTypeFactory().constructType(Properties.class);
        return MAPPER.readValue(inputStream, mapType);
    }

    @SneakyThrows
    public <T> T toBean(String json, TypeReference<T> typeReference) {
        return check(json, typeReference) ? null :
                MAPPER.readValue(json, typeReference);
    }

    @SneakyThrows
    public JsonNode readTree(String json) {
        return MAPPER.readTree(json);
    }

    private boolean check(String json, Object... checkObj) {
        return json == null || json.isEmpty() || ObjectUtils.anyChecked(Objects::isNull, checkObj);
    }
}
