package com.mobaijun.json.gson.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Description: [Gson解析的Number类型的字段解析适配器]
 * Author: [mobaijun]
 * Date: [2024/1/30 14:31]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class NumberTypeAdapter<T> extends TypeAdapter<Number> {
    private final Class<T> clazz;

    public NumberTypeAdapter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void write(JsonWriter jsonWriter, Number number) throws IOException {
        // 将数字写入 JSON
        if (number != null) {
            jsonWriter.value(number);
        } else {
            // 写入 null 值
            jsonWriter.nullValue();
        }
    }

    @Override
    public Number read(JsonReader jsonReader) throws IOException {
        try {
            // 检查 JSON 中是否为 null
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }

            // 读取 JSON 中的字符串
            String json = jsonReader.nextString();

            // 检查字符串是否为空
            if (Objects.isNull(json) || json.trim().isEmpty()) {
                return null;
            }

            // 根据类型进行相应的转换
            if (clazz == short.class || clazz == Short.class) {
                return Short.parseShort(json);
            } else if (clazz == int.class || clazz == Integer.class) {
                return Integer.parseInt(json);
            } else if (clazz == long.class || clazz == Long.class) {
                return Long.parseLong(json);
            } else if (clazz == float.class || clazz == Float.class) {
                return Float.parseFloat(json);
            } else if (clazz == double.class || clazz == Double.class) {
                return Double.parseDouble(json);
            } else if (clazz == BigDecimal.class) {
                return new BigDecimal(json);
            } else {
                // 默认情况，尝试解析为整数
                return Integer.parseInt(json);
            }
        } catch (NumberFormatException e) {
            // 处理异常，可以根据具体情况进行适当的处理
            return null;
        }
    }
}
