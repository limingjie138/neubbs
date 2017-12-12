package org.neusoft.neubbs.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author Suvan
 */
public final class JsonUtil {

    private JsonUtil() { }

    /**
     * 将Object对象转换为JSON格式字符串
     *
     * @param obj Object对象
     * @return String JSON格式字符串
     */
    public static String toJSONStringByObject(Object obj) {
        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException jpe) { }

        return json;
    }

    /**
     * 将JSON格式字符串，转为Map<String,String>
     *
     * @param json JSON格式字符串
     * @return Map<String,Object>键值对
     */
    public static Map<String, Object> toMapByJSONString(String json) {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = null;
        try {
            map = mapper.readValue(json, LinkedHashMap.class);
        } catch (IOException ioe) { }

        return map;
    }

    /**
     * 将 Object 对象转换成 Map<String, String> 格式
     *
     * @param obj Object对象
     * @return Map<Sttring,Object>键值对
     */
    public static Map<String, Object> toMapByObject(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        Map<String, Object> map = null;

        try {
            json = mapper.writeValueAsString(obj);
            map = mapper.readValue(json, LinkedHashMap.class);
        } catch (IOException e) { }

        return map;
    }

    /**
     * 判断 JSON 数组是否存在指定 int 元素
     *
     * @param jsonArrayString JSON数组字符串
     * @param number 指定int元素
     * @return boolean 判断结果（true-存在，false-不存在）
     */
    public static boolean isJsonArrayStringExistIntElement(String jsonArrayString,  int number) {
        JSONArray jsonArray = JSON.parseArray(jsonArrayString);

        boolean result = false;
        for (int i = 0, len = jsonArray.size(); i < len; i++) {
            if (jsonArray.getInteger(i) == number) {
                result = true;
                break;
            }
        }

        return result;
    }
}
