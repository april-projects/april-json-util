# April JSON Util

April JSON Util 是一个简单的Java JSON工具库，提供了对 Gson 和 FastJson 的简单封装，使 JSON 处理更加方便。

## 特性

- 封装了 Gson 和 FastJson，简化了 JSON 操作
- 支持常见的 JSON 转换操作
- 使用 Apache License, Version 2.0 开源许可证

## 模块

### 1. GsonUtil

GsonUtil 模块提供了使用 Gson 进行 JSON 操作的工具类。

#### 使用示例：

```java
// 获取 Gson 实例
Gson gson = GsonUtil.getGsonInstance();

// 将对象转换为 JSON 字符串
String jsonString = GsonUtil.toJson(myObject);

// 将 JSON 字符串转换为对象
MyClass obj = GsonUtil.toBean(jsonString, MyClass.class);

// 将 JSON 字符串转换为指定类型的对象
MyClass objWithType = GsonUtil.toBean(jsonString, new TypeToken<MyClass>() {
}.getType());

// 将 JSON 字符串转换为 List
List<MyClass> list = GsonUtil.toList(jsonString);

// 将 JSON 字符串转换为 Set
Set<MyClass> set = GsonUtil.toSet(jsonString);

// 将 JSON 字符串转换为 List 中有 Map 的
List<Map<String, MyClass>> listMap = GsonUtil.toListMap(jsonString);

// 将 JSON 字符串转换为 Map
Map<String, MyClass> map = GsonUtil.toMap(jsonString);

// 序列化对象列表为 JSON 文件
GsonUtil.

toFile("path/to/file.json",myList);

// 序列化单个对象为 JSON 文件
GsonUtil.

toFile("path/to/file.json",myObject);

// 从 JSON 字符串中获取某个字段的值
String fieldValue = GsonUtil.getStringValue(jsonString, "fieldName");

// 从 JSON 字符串中获取某个字段的 JsonObject
JsonElement jsonObjectForKey = GsonUtil.getJsonObjectForKey(jsonString, "fieldName");

// 向 JSON 字符串中添加属性
String jsonWithNewProperty = GsonUtil.add(jsonString, "newField", "value");

// 从 JSON 字符串中移除某个属性
String jsonWithoutProperty = GsonUtil.remove(jsonString, "fieldName");

// 修改 JSON 字符串中的属性
String jsonWithUpdatedProperty = GsonUtil.update(jsonString, "fieldName", "newValue");

// 格式化 JSON 字符串
String formattedJson = GsonUtil.format(jsonString);

// 判断字符串是否为 JSON
boolean isJson = GsonUtil.isJson(jsonString);

// 将 JSON 字符串解析为 JsonObject
JsonObject jsonObject = GsonUtil.parseStringToJson(jsonString);
```

### 2. FastJsonUtil

FastJsonUtil 模块提供了使用 FastJson 进行 JSON 操作的工具类。

#### 使用示例：

~~~java
// 从输入流中反序列化JSON为对象
FastJsonUtil.toJson(inputStream, MyClass .class);

// 从JSON字符串中反序列化为对象
FastJsonUtil.

toBean(jsonString, MyClass .class);

// 从JSON字符串中反序列化为List对象
FastJsonUtil.

toList(jsonString, MyClass .class);

// 从JSON字符串中反序列化为Map对象
FastJsonUtil.

toMap(jsonString);

// 将List对象序列化为JSON字符串
FastJsonUtil.

toJson(myList);

// 将对象序列化为JSON字符串
FastJsonUtil.

toJson(myObject);

// 从JSON字符串中获取指定字段的字符串值
FastJsonUtil.

getStr(jsonString, "fieldName");

// 从JSON字符串中获取指定字段的整数值
FastJsonUtil.

getInt(jsonString, "fieldName");

// 从JSON字符串中获取指定字段的长整型值
FastJsonUtil.

getLong(jsonString, "fieldName");

// 从JSON字符串中获取指定字段的双精度浮点数值
FastJsonUtil.

getDouble(jsonString, "fieldName");

// 从JSON字符串中获取指定字段的大整数值
FastJsonUtil.

getInteger(jsonString, "fieldName");

// 从JSON字符串中获取指定字段的大十进制数值
FastJsonUtil.

getBigDecimal(jsonString, "fieldName");

// 从JSON字符串中获取指定字段的布尔值
FastJsonUtil.

getBoolean(jsonString, "fieldName");

// 从JSON字符串中获取指定字段的字节值
FastJsonUtil.

getByte(jsonString, "fieldName");

// 从JSON字符串中获取指定字段的对象值
FastJsonUtil.

getObject(jsonString, "fieldName",MyObject .class);

// 从JSON字符串中获取指定字段的列表值
FastJsonUtil.

getList(jsonString, "fieldName",MyObject .class);

// 从JSON字符串中获取指定字段的JSONObject值
FastJsonUtil.

getJsonObject(jsonString, "fieldName");

// 向JSON字符串中添加属性
FastJsonUtil.

addProperty(jsonString, "newField","value");

// 从JSON字符串中移除指定字段
FastJsonUtil.

removeProperty(jsonString, "fieldName");

// 修改JSON字符串中指定字段的值
FastJsonUtil.

updateProperty(jsonString, "fieldName","newValue");

// 格式化JSON字符串（美化）
FastJsonUtil.

formatJson(jsonString);

// 判断字符串是否是JSON格式
FastJsonUtil.

isJson(jsonString);
~~~

## 开始使用

### Maven 依赖

* 在你的项目中添加以下 Maven 依赖：

~~~xml

<dependency>
    <groupId>com.mobaijun</groupId>
    <artifactId>april-json-util</artifactId>
    <!-- 使用最新版本 -->
    <version>1.0.0</version>
</dependency>
        <!-- 根据项目中使用的 json 工具引入依赖 -->
        <!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
<dependency>
	<groupId>com.google.code.gson</groupId>
	<artifactId>gson</artifactId>
	<version>${gson.version}</version>
</dependency>
        <!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>fastjson</artifactId>
	<version>${fastjson.version}</version>
</dependency>
~~~

## 贡献

如果你有兴趣为 April JSON Util 做出贡献，欢迎提交 Pull Request。在提交之前，请确保你的代码通过单元测试，并遵循项目的代码规范。

## 许可证

April JSON Util 使用 Apache License, Version 2.0 开源许可证。更多详情请参阅 LICENSE 文件。

## 联系我们

如果你有任何问题或建议，请通过 GitHub Issues 联系我们。