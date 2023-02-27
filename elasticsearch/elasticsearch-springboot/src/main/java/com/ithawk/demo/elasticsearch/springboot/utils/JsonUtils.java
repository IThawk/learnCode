package com.ithawk.demo.elasticsearch.springboot.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.action.search.SearchResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: JsonUtils.java
 * @Description: json转换类
 */

@Slf4j
public class JsonUtils {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     *
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
           log.error("objectToJson error:{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            log.error("objectToJson error:{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            log.error("jsonToList error:{}", ExceptionUtils.getStackTrace(e));
        }

        return null;
    }

    public static <T> List<T> searchResponseHitsToList(SearchResponse searchResponse, Class<T> beanType) {
        try {
            return Arrays.stream(searchResponse.getHits().getHits())
                    .map(v -> {
                        try {
                            return MAPPER.readValue(v.getSourceAsString(), beanType);
                        } catch (JsonProcessingException e) {
                            log.error("searchResponseHitsToList error:{}", ExceptionUtils.getStackTrace(e));
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("searchResponseHitsToList error:{}", ExceptionUtils.getStackTrace(e));
        }

        return null;
    }

}
