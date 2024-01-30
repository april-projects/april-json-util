/*
 * Copyright (C) 2022 [mobaijun]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobaijun.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mobaijun.json.fastjson.exception.FastJsonException;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

/**
 * Description: [ FastJson工具类，提供JSON序列化和反序列化的方法 ]
 * Author: [mobaijun]
 * Date: [2024/1/30 11:39]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class FastJsonUtil {

    /**
     * 从输入流中反序列化JSON
     *
     * @param inputStream 输入流
     * @param type        目标类型的Class
     * @param <V>         泛型类型
     * @return 反序列化得到的对象
     * @throws FastJsonException 反序列化异常
     */
    public static <V> V toJson(InputStream inputStream, Class<V> type) {
        try {
            return JSON.parseObject(inputStream, type);
        } catch (IOException e) {
            throw new FastJsonException("FastJson deserialization error, type: {}", type, e);
        }
    }

    /**
     * 从输入流中反序列化JSON
     *
     * @param inputStream   输入流
     * @param typeReference 目标类型的TypeReference
     * @param <V>           泛型类型
     * @return 反序列化得到的对象
     * @throws FastJsonException 反序列化异常
     */
    public static <V> V toJson(InputStream inputStream, TypeReference<V> typeReference) {
        try {
            return JSON.parseObject(inputStream, typeReference.getType());
        } catch (IOException e) {
            throw new FastJsonException("FastJson deserialization error, type: {}", typeReference, e);
        }
    }

    /**
     * 从JSON字符串中反序列化对象
     *
     * @param json JSON字符串
     * @param type 目标类型的Class
     * @param <V>  泛型类型
     * @return 反序列化得到的对象
     */
    public static <V> V toBean(String json, Class<V> type) {
        return JSON.parseObject(json, type);
    }

    /**
     * 从JSON字符串中反序列化对象
     *
     * @param json          JSON字符串
     * @param typeReference 目标类型的TypeReference
     * @param <V>           泛型类型
     * @return 反序列化得到的对象
     */
    public static <V> V toBean(String json, TypeReference<V> typeReference) {
        return JSON.parseObject(json, typeReference.getType());
    }

    /**
     * 从JSON字符串中反序列化List对象
     *
     * @param json JSON字符串
     * @param type 目标类型的Class
     * @param <V>  泛型类型
     * @return 反序列化得到的List对象
     */
    public static <V> List<V> toList(String json, Class<V> type) {
        return JSON.parseArray(json, type);
    }

    /**
     * 从JSON字符串中反序列化为Map对象
     *
     * @param json JSON字符串
     * @return 反序列化得到的Map对象
     */
    public static <T> HashMap<String, T> toMap(String json) {
        return JSON.parseObject(json, new TypeReference<HashMap<String, T>>() {
        });
    }

    /**
     * 将对象序列化为JSON字符串
     *
     * @param list 要序列化的List对象
     * @param <V>  泛型类型
     * @return 序列化得到的JSON字符串
     */
    public static <V> String toJson(List<V> list) {
        return JSON.toJSONString(list);
    }

    /**
     * 将对象序列化为JSON字符串
     *
     * @param obj 要序列化的对象
     * @param <V> 泛型类型
     * @return 序列化得到的JSON字符串
     */
    public static <V> String toJson(V obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 从JSON字符串中获取指定字段的字符串值
     *
     * @param json JSON字符串
     * @param key  字段名
     * @return 字段的字符串值，如果字段不存在或发生异常则返回null
     * @throws FastJsonException 获取字符串异常
     */
    public static String getStr(String json, String key) {
        if (json.isEmpty()) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return null;
            }
            return jsonObject.getString(key);
        } catch (Exception e) {
            throw new FastJsonException("FastJson string retrieval error, JSON: {}, field: {}", json, key, e);
        }
    }

    /**
     * 从JSON字符串中获取指定字段的整数值
     *
     * @param json JSON字符串
     * @param key  字段名
     * @return 字段的整数值，如果字段不存在或发生异常则返回0
     * @throws FastJsonException 获取整数异常
     */
    public static int getInt(String json, String key) {
        if (json.isEmpty()) {
            return 0;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return 0;
            }
            return jsonObject.getInteger(key);
        } catch (Exception e) {
            throw new FastJsonException("FastJson integer retrieval error, JSON: {}, field: {}", json, key, e);
        }
    }


    /**
     * 从JSON字符串中获取指定字段的长整型值
     *
     * @param json JSON字符串
     * @param key  字段名
     * @return 字段的长整型值，如果字段不存在或发生异常则返回0L
     * @throws FastJsonException 获取长整型异常
     */
    public static long getLong(String json, String key) {
        if (json.isEmpty()) {
            return 0L;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return 0L;
            }
            return jsonObject.getLongValue(key);
        } catch (Exception e) {
            throw new FastJsonException("FastJson long retrieval error, JSON: {}, field: {}", json, key, e);
        }
    }

    /**
     * 从JSON字符串中获取指定字段的双精度浮点数值
     *
     * @param json JSON字符串
     * @param key  字段名
     * @return 字段的双精度浮点数值，如果字段不存在或发生异常则返回0.0
     * @throws FastJsonException 获取双精度浮点数异常
     */
    public static double getDouble(String json, String key) {
        if (json.isEmpty()) {
            return 0.0;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return 0.0;
            }
            return jsonObject.getDoubleValue(key);
        } catch (Exception e) {
            throw new FastJsonException("FastJson double retrieval error, JSON: {}, field: {}", json, key, e);
        }
    }

    /**
     * 从JSON字符串中获取指定字段的大整数值
     *
     * @param json JSON字符串
     * @param key  字段名
     * @return 字段的大整数值，如果字段不存在或发生异常则返回0.0
     * @throws FastJsonException 获取大整数异常
     */
    public static BigInteger getInteger(String json, String key) {
        if (json.isEmpty()) {
            return BigInteger.valueOf(0L);
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return BigInteger.valueOf(0L);
            }
            return jsonObject.getBigInteger(key);
        } catch (Exception e) {
            throw new FastJsonException("FastJson big integer retrieval error, JSON: {}, field: {}", json, key, e);
        }
    }

    /**
     * 从JSON字符串中获取指定字段的大十进制数值
     *
     * @param json JSON字符串
     * @param key  字段名
     * @return 字段的大十进制数值，如果字段不存在或发生异常则返回0.0
     * @throws FastJsonException 获取大十进制数异常
     */
    public static BigDecimal getBigDecimal(String json, String key) {
        if (json.isEmpty()) {
            return BigDecimal.valueOf(0.0);
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return BigDecimal.valueOf(0.0);
            }
            return jsonObject.getBigDecimal(key);
        } catch (Exception e) {
            throw new FastJsonException("FastJson big decimal retrieval error, JSON: {}, field: {}", json, key, e);
        }
    }

    /**
     * 从JSON字符串中获取指定字段的布尔值
     *
     * @param json JSON字符串
     * @param key  字段名
     * @return 字段的布尔值，如果字段不存在或发生异常则返回false
     * @throws FastJsonException 获取布尔值异常
     */
    public static boolean getBoolean(String json, String key) {
        if (json.isEmpty()) {
            return false;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return false;
            }
            return jsonObject.getBooleanValue(key);
        } catch (Exception e) {
            throw new FastJsonException("FastJson boolean retrieval error, JSON: {}, field: {}", json, key, e);
        }
    }

    /**
     * 从JSON字符串中获取指定字段的字节值
     *
     * @param json JSON字符串
     * @param key  字段名
     * @return 字段的字节值，如果字段不存在或发生异常则返回0
     * @throws FastJsonException 获取字节值异常
     */
    public static byte getByte(String json, String key) {
        if (json.isEmpty()) {
            return 0;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return 0;
            }
            return jsonObject.getByteValue(key);
        } catch (Exception e) {
            throw new FastJsonException("FastJson byte retrieval error, JSON: {}, field: {}", json, key, e);
        }
    }

    /**
     * 从JSON字符串中获取指定字段的对象值
     *
     * @param json JSON字符串
     * @param key  字段名
     * @param type 对象类型的Class
     * @param <V>  泛型类型
     * @return 字段的对象值，如果字段不存在或发生异常则返回null
     * @throws FastJsonException 获取对象值异常
     */
    public static <V> V getObject(String json, String key, Class<V> type) {
        if (json.isEmpty()) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return null;
            }
            return JSON.parseObject(jsonObject.getString(key), type);
        } catch (Exception e) {
            throw new FastJsonException("FastJson object retrieval error, JSON: {}, field: {}, type: {}", json, key, type, e);
        }
    }

    /**
     * 从JSON字符串中获取指定字段的列表值
     *
     * @param json JSON字符串
     * @param key  字段名
     * @param type 列表元素类型的Class
     * @param <V>  泛型类型
     * @return 字段的列表值，如果字段不存在或发生异常则返回null
     * @throws FastJsonException 获取列表值异常
     */
    public static <V> List<V> getList(String json, String key, Class<V> type) {
        if (json.isEmpty()) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return null;
            }
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            return jsonArray.toJavaList(type);
        } catch (Exception e) {
            throw new FastJsonException("FastJson list retrieval error, JSON: {}, field: {}, type: {}", json, key, type, e);
        }
    }

    /**
     * 从JSON字符串中获取指定字段的JSONObject值
     *
     * @param json JSON字符串
     * @param key  字段名
     * @return 字段的JSONObject值，如果字段不存在或发生异常则返回null
     * @throws FastJsonException 获取JSONObject值异常
     */
    public static JSONObject getJsonObject(String json, String key) {
        if (json.isEmpty()) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return null;
            }
            return jsonObject.getJSONObject(key);
        } catch (Exception e) {
            throw new FastJsonException("FastJson JSONObject retrieval error, JSON: {}, field: {}", json, key, e);
        }
    }

    /**
     * 向JSON字符串中添加属性
     *
     * @param json  JSON字符串
     * @param key   字段名
     * @param value 字段值
     * @param <V>   泛型类型
     * @return 添加属性后的JSON字符串
     */
    public static <V> String addProperty(String json, String key, V value) {
        JSONObject jsonObject = JSON.parseObject(json);
        addProperty(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * 向JSONObject中添加属性
     *
     * @param jsonObject JSONObject对象
     * @param key        字段名
     * @param value      字段值
     * @param <V>        泛型类型
     */
    private static <V> void addProperty(JSONObject jsonObject, String key, V value) {
        if (value instanceof String || value instanceof Number || value instanceof Boolean || value instanceof Byte[]) {
            jsonObject.put(key, value);
        } else {
            jsonObject.put(key, toJson(value));
        }
    }

    /**
     * 从JSON字符串中移除指定字段
     *
     * @param json JSON字符串
     * @param key  字段名
     * @return 移除指定字段后的JSON字符串
     */
    public static String removeProperty(String json, String key) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(key);
        return jsonObject.toString();
    }

    /**
     * 修改JSON字符串中指定字段的值
     *
     * @param json  JSON字符串
     * @param key   字段名
     * @param value 新的字段值
     * @param <V>   泛型类型
     * @return 修改字段值后的JSON字符串
     */
    public static <V> String updateProperty(String json, String key, V value) {
        JSONObject jsonObject = JSON.parseObject(json);
        addProperty(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * 格式化JSON字符串（美化）
     *
     * @param json JSON字符串
     * @return 格式化后的JSON字符串
     */
    public static String formatJson(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        return JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
    }

    /**
     * 判断字符串是否是JSON格式
     *
     * @param json 字符串
     * @return 如果是JSON格式返回true，否则返回false
     */
    public static boolean isJson(String json) {
        try {
            JSON.parse(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
