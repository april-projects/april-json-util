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
package com.mobaijun.json.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.mobaijun.json.gson.adapter.NumberTypeAdapter;
import com.mobaijun.json.gson.exception.GsonException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: [gson 工具类]
 * Author: [mobaijun]
 * Date: [2024/1/29 16:41]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class GsonUtil {

    /**
     * 私有静态 final 字段确保在整个应用程序中只有一个 Gson 实例
     */
    private static final Gson GSON_INSTANCE;

    // 日期时间格式化器
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * LocalDateTime序列化器
     */
    private static final JsonSerializer<LocalDateTime> dateTimeSerializer
            = (obj, type, ctx) -> new JsonPrimitive(dateTimeFormatter.format(obj));

    /**
     * LocalDate序列化器
     */
    private static final JsonSerializer<LocalDate> dateSerializer
            = (obj, type, ctx) -> new JsonPrimitive(dateFormatter.format(obj));

    /**
     * LocalTime序列化器
     */
    private static final JsonSerializer<LocalTime> timeSerializer
            = (obj, type, ctx) -> new JsonPrimitive(timeFormatter.format(obj));

    /**
     * LocalDateTime反序列化器
     */
    private static final JsonDeserializer<LocalDateTime> dateTimeDeserializer
            = (json, type, ctx) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), dateTimeFormatter);

    /**
     * LocalDate反序列化器
     */
    private static final JsonDeserializer<LocalDate> dateDeserializer
            = (json, type, ctx) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString(), dateFormatter);

    /**
     * LocalTime反序列化器
     */
    private static final JsonDeserializer<LocalTime> timeDeserializer
            = (json, type, ctx) -> LocalTime.parse(json.getAsJsonPrimitive().getAsString(), timeFormatter);

    static {
        // 使用 GsonBuilder 进行配置
        GsonBuilder gsonBuilder = new GsonBuilder();

        // 可选：设置日期格式
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, dateTimeSerializer);
        gsonBuilder.registerTypeAdapter(LocalDate.class, dateSerializer);
        gsonBuilder.registerTypeAdapter(LocalTime.class, timeSerializer);
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, dateTimeDeserializer);
        gsonBuilder.registerTypeAdapter(LocalDate.class, dateDeserializer);
        gsonBuilder.registerTypeAdapter(LocalTime.class, timeDeserializer);
        // 可选：启用/禁用 HTML 转义
        gsonBuilder.disableHtmlEscaping();

        // 当Map的key为复杂对象时,需要开启该方法
        gsonBuilder.enableComplexMapKeySerialization();

        // 可选：设置字段名称格式
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        // 注册基本数据类型
        registerTypeAdapter(gsonBuilder);
        // 构建 Gson 实例
        GSON_INSTANCE = gsonBuilder.create();
    }

    private static void registerTypeAdapter(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(short.class, new NumberTypeAdapter<>(short.class));
        gsonBuilder.registerTypeAdapter(Short.class, new NumberTypeAdapter<>(Short.class));
        gsonBuilder.registerTypeAdapter(int.class, new NumberTypeAdapter<>(int.class));
        gsonBuilder.registerTypeAdapter(Integer.class, new NumberTypeAdapter<>(Integer.class));
        gsonBuilder.registerTypeAdapter(long.class, new NumberTypeAdapter<>(long.class));
        gsonBuilder.registerTypeAdapter(Long.class, new NumberTypeAdapter<>(Long.class));
        gsonBuilder.registerTypeAdapter(float.class, new NumberTypeAdapter<>(float.class));
        gsonBuilder.registerTypeAdapter(Float.class, new NumberTypeAdapter<>(Float.class));
        gsonBuilder.registerTypeAdapter(double.class, new NumberTypeAdapter<>(double.class));
        gsonBuilder.registerTypeAdapter(Double.class, new NumberTypeAdapter<>(Double.class));
        gsonBuilder.registerTypeAdapter(BigDecimal.class, new NumberTypeAdapter<>(BigDecimal.class));
    }

    /**
     * 获取 Gson 实例
     *
     * @return Gson 实例
     */
    public static Gson getGsonInstance() {
        return GSON_INSTANCE;
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param entity 要转换的对象
     * @param <T>    对象的类型
     * @return 转换后的JSON字符串，如果转换失败则返回null
     */
    public static <T> String toJson(T entity) {
        String gsonString = null;
        if (getGsonInstance() != null) {
            gsonString = getGsonInstance().toJson(entity);
        }
        return gsonString;
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json   JSON字符串
     * @param entity 对象的类型
     * @param <T>    对象的类型
     * @return 转换后的对象
     */
    public static <T> T toBean(String json, Class<T> entity) {
        return GSON_INSTANCE.fromJson(json, entity);
    }


    /**
     * 将JSON字符串转换为指定类型的对象
     *
     * @param json JSON字符串
     * @param type 转换后的对象类型，可以使用TypeToken获取
     * @param <T>  对象的类型
     * @return 转换后的对象
     */
    public static <T> T toBean(String json, Type type) {
        return GSON_INSTANCE.fromJson(json, type);
    }

    /**
     * 将JSON字符串转换为List
     *
     * @param json JSON字符串
     * @param <T>  List中元素的类型
     * @return 转换后的List
     */
    public static <T> List<T> toList(String json) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("JSON字符串不能为空");
        }
        return GSON_INSTANCE.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    /**
     * 将JSON字符串转换为指定类型的元素组成的Set集合。
     *
     * @param json JSON字符串，将其转换为Set集合。
     * @param <T>  Set集合中元素的类型。
     * @return 包含指定类型元素的Set集合。
     * @throws IllegalArgumentException 如果提供的JSON字符串为null或为空。
     */
    public static <T> Set<T> toSet(String json) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("JSON字符串不能为空");
        }
        return GSON_INSTANCE.fromJson(json, (new TypeToken<Set<T>>() {
        }).getType());
    }

    /**
     * 将JSON字符串转换为List中有Map的
     *
     * @param json JSON字符串
     * @param <T>  Map中值的类型
     * @return 转换后的List
     */
    public static <T> List<Map<String, T>> toListMap(String json) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("JSON字符串不能为空");
        }
        return GSON_INSTANCE.fromJson(json, new TypeToken<List<Map<String, T>>>() {
        }.getType());
    }

    /**
     * 将JSON字符串转换为Map
     *
     * @param json JSON字符串
     * @param <T>  Map中值的类型
     * @return 转换后的Map
     */
    public static <T> Map<String, T> toMap(String json) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("JSON字符串不能为空");
        }
        return GSON_INSTANCE.fromJson(json, new TypeToken<Map<String, ?>>() {
        }.getType());
    }

    /**
     * 序列化对象列表为 JSON 文件。
     *
     * @param path JSON 文件路径
     * @param list 要序列化的对象列表
     * @param <V>  对象类型
     * @throws GsonException 如果序列化出现异常
     */
    public static <V> void toFile(String path, List<V> list) {
        try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(path, true))) {
            GSON_INSTANCE.toJson(list, new TypeToken<List<V>>() {
            }.getType(), jsonWriter);
            jsonWriter.flush();
        } catch (JsonIOException | IOException e) {
            throw new GsonException("Error while serializing list to JSON file. Path: {}, List: {}", path, list, e);
        }
    }

    /**
     * 序列化单个对象为 JSON 文件。
     *
     * @param path JSON 文件路径
     * @param obj  要序列化的对象
     * @param <V>  对象类型
     * @throws GsonException 如果序列化出现异常
     */
    public static <V> void toFile(String path, V obj) {
        try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(path, true))) {
            GSON_INSTANCE.toJson(obj, obj.getClass(), jsonWriter);
            jsonWriter.flush();
        } catch (JsonIOException | IOException e) {
            throw new GsonException("Error while serializing object to JSON file. Path: {}, Object: {}", path, obj, e);
        }
    }

    /**
     * 从 JSON 文件中反序列化为对象列表。
     *
     * @param path JSON 文件路径
     * @param type 对象的类型
     * @param <V>  对象类型
     * @return 反序列化得到的对象列表
     * @throws GsonException 如果反序列化出现异常
     */
    public static <V> List<V> toFileList(String path, Class<V> type) {
        try (FileReader fileReader = new FileReader(path)) {
            return GSON_INSTANCE.fromJson(fileReader, TypeToken.getParameterized(List.class, type).getType());
        } catch (JsonIOException | IOException | JsonSyntaxException e) {
            throw new GsonException("Error while deserializing JSON file to list. Path: {}, Type: {}", path, type, e);
        }
    }

    /**
     * 从 JSON 文件中反序列化为对象。
     *
     * @param path JSON 文件路径
     * @param type 对象的类型
     * @param <V>  对象类型
     * @return 反序列化得到的对象
     * @throws GsonException 如果反序列化出现异常
     */
    public static <V> V toFileBean(String path, Class<V> type) {
        try (FileReader fileReader = new FileReader(path)) {
            return GSON_INSTANCE.fromJson(fileReader, type);
        } catch (JsonIOException | IOException | JsonSyntaxException e) {
            throw new GsonException("Error while deserializing JSON file to object. Path: {}, Type: {}", path, type, e);
        }
    }

    /**
     * 从 JSON 字符串中获取某个字段的值。
     *
     * @param json JSON 字符串
     * @param key  要获取的字段的键
     * @return 字段的值，如果不存在返回 null
     */
    public static String getStringValue(String json, String key) {
        if (json.isEmpty()) {
            return null;
        }

        JsonElement jsonByKey = getJsonObjectForKey(json, key);
        if (jsonByKey == null) {
            return null;
        }

        try {
            return jsonByKey.getAsString();
        } catch (Exception e) {
            return jsonByKey.toString();
        }
    }

    /**
     * 从 JSON 字符串中获取某个字段的 JsonObject。
     *
     * @param json JSON 字符串
     * @param key  要获取的字段的键
     * @return 字段的 JsonObject，如果不存在返回 null
     * @throws GsonException 如果 JSON 解析出现异常
     */
    public static JsonElement getJsonObjectForKey(String json, String key) {
        try {
            JsonElement element = JsonParser.parseString(json);
            JsonObject jsonObj = element.getAsJsonObject();
            return jsonObj.get(key);
        } catch (JsonSyntaxException e) {
            throw new GsonException("Error while getting object from JSON. JSON: {}, Key: {}", json, key, e);
        }
    }

    /**
     * 向 JSON 字符串中添加属性。
     *
     * @param json  JSON 字符串
     * @param key   要添加的属性的键
     * @param value 要添加的属性的值
     * @param <V>   属性的值的类型
     * @return 添加属性后的 JSON 字符串
     */
    public static <V> String add(String json, String key, V value) {
        JsonElement element = JsonParser.parseString(json);
        JsonObject jsonObject = element.getAsJsonObject();
        add(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * 向 JsonObject 中添加属性。
     *
     * @param jsonObject JsonObject
     * @param key        要添加的属性的键
     * @param value      要添加的属性的值
     * @param <V>        属性的值的类型
     */
    private static <V> void add(JsonObject jsonObject, String key, V value) {
        if (value instanceof String || value instanceof Number) {
            jsonObject.addProperty(key, value.toString());
        } else {
            jsonObject.addProperty(key, toJson(value));
        }
    }

    /**
     * 从 JSON 字符串中移除某个属性。
     *
     * @param json JSON 字符串
     * @param key  要移除的属性的键
     * @return 移除属性后的 JSON 字符串
     */
    public static String remove(String json, String key) {
        JsonElement element = JsonParser.parseString(json);
        JsonObject jsonObj = element.getAsJsonObject();
        jsonObj.remove(key);
        return jsonObj.toString();
    }

    /**
     * 修改 JSON 字符串中的属性。
     *
     * @param json  JSON 字符串
     * @param key   要修改的属性的键
     * @param value 要修改的属性的值
     * @param <V>   属性的值的类型
     * @return 修改属性后的 JSON 字符串
     */
    public static <V> String update(String json, String key, V value) {
        JsonElement element = JsonParser.parseString(json);
        JsonObject jsonObject = element.getAsJsonObject();
        jsonObject.remove(key);
        add(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * 格式化 JSON 字符串（美化）。
     *
     * @param json JSON 字符串
     * @return 格式化后的 JSON 字符串
     */
    public static String format(String json) {
        JsonElement jsonElement = JsonParser.parseString(json);
        return GSON_INSTANCE.toJson(jsonElement);
    }

    /**
     * 判断字符串是否为 JSON。
     *
     * @param json 字符串
     * @return 如果是 JSON 则返回 true，否则返回 false
     */
    public static boolean isJson(String json) {
        try {
            return JsonParser.parseString(json).isJsonObject();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将 JSON 字符串解析为 JsonObject。
     *
     * @param jsonString JSON 字符串
     * @return JsonObject
     */
    public static JsonObject parseStringToJson(String jsonString) {
        return JsonParser.parseString(jsonString).getAsJsonObject();
    }
}
