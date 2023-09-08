package com.icchance.q91.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

/**
 * <p>
 * JSON轉換工具類
 * </p>
 * @author 6687353
 * @since 2023/9/8 11:43:28
 */
public class JacksonUtil {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readValue(String jsonStr, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(InputStream fileInputStream, Class<T> valueType) {
        try {
            return objectMapper.readValue(fileInputStream, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(InputStream fileInputStream, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(fileInputStream, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
