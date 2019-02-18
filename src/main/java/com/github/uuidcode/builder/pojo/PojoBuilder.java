package com.github.uuidcode.builder.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.uuidcode.util.CoreUtil;

import static com.github.uuidcode.builder.pojo.Property.TYPE_DATE;
import static com.github.uuidcode.builder.pojo.Property.TYPE_LONG;
import static com.github.uuidcode.builder.pojo.Property.TYPE_STRING;
import static com.github.uuidcode.util.CoreUtil.splitListWithSpace;
import static com.github.uuidcode.util.CoreUtil.toFirstCharUpperCase;

public class PojoBuilder {
    public static Map<String, String> nameConverterMap = new HashMap<>();
    public static Set<String> includedFieldSet = new HashSet<>();
    public static Set<String> excludedFieldSet = new HashSet<>();
    public static Map<String, String> typeConvertMap = new HashMap<String, String>() {{
        this.put("int", TYPE_LONG);
        this.put("bigint", TYPE_LONG);
        this.put("varbinary", TYPE_STRING);
        this.put("enum", TYPE_STRING);
        this.put("varchar", TYPE_STRING);
        this.put("char", TYPE_STRING);
        this.put("timestamp", TYPE_DATE);
        this.put("date", TYPE_DATE);
    }};

    private String className;
    private String targetDirectory;
    private String packageName;
    private List<Property> propertyList;

    public String getPackageName() {
        return this.packageName;
    }

    public PojoBuilder setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getTargetDirectory() {
        return this.targetDirectory;
    }

    public static boolean isAvailableField(String name) {
        if (includedFieldSet.isEmpty() && excludedFieldSet.isEmpty()) {
            return true;
        }


        if (includedFieldSet.isEmpty()) {
            return !excludedFieldSet.contains(name);
        }

        if (excludedFieldSet.isEmpty()) {
            return includedFieldSet.contains(name);
        }

        return false;
    }

    public PojoBuilder setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
        return this;
    }

    public String getClassName() {
        return this.className;
    }

    public PojoBuilder setClassName(String className) {
        this.className = className;
        return this;
    }

    public PojoBuilder setSchema(String schema) {
        this.propertyList = CoreUtil.splitListWithNewLine(schema)
            .stream()
            .map(line -> {
                line = line.trim();
                List<String> itemList = splitListWithSpace(line);
                String name = itemList.get(0).replaceAll("`", "");
                String type = itemList.get(1);
                String convertedType = this.getConvertedType(type);

                return Property.of()
                    .setName(name)
                    .setType(convertedType)
                    .setIsList(false);
            })
            .collect(Collectors.toList());

        return this;
    }

    public PojoBuilder setJson(String json) {
        Map<String, Object> map = CoreUtil.fromJsonToMap(json);
        this.propertyList = getPropertyListFromMap(map);
        return this;
    }

    public static List<Property> getPropertyListFromMap(Map<String, Object> map) {
        return map.entrySet()
            .stream()
            .filter(entry -> PojoBuilder.isAvailableField(entry.getKey()))
            .map(Property::of)
            .collect(Collectors.toList());
    }

    private String getConvertedType(String type) {
        return typeConvertMap.entrySet()
            .stream()
            .filter(entry -> entry.getKey().startsWith(type))
            .findFirst()
            .map(Entry::getValue)
            .orElse(TYPE_STRING);
    }

    public static String getJavaType(String name) {
        if (name.endsWith("List")) {
            name = name.substring(0, name.length() - 4);
            name = toFirstCharUpperCase(name);
        }

        String type = nameConverterMap.getOrDefault(name, name);
        return toFirstCharUpperCase(type);
    }

    public PojoBuilder addNameConvert(String oldName, String newName) {
        nameConverterMap.put(oldName, newName);
        return this;
    }

    public PojoBuilder addIncludeField(String field) {
        includedFieldSet.add(field);
        return this;
    }

    public PojoBuilder addExcludeField(String field) {
        excludedFieldSet.add(field);
        return this;
    }

    public static PojoBuilder of() {
        return new PojoBuilder();
    }

    public void build() {
        List<Pojo> pojoList = Pojo.of()
            .setPackageName(this.packageName)
            .setClassName(this.className)
            .setPropertyList(this.propertyList)
            .build();

        pojoList
            .stream()
            .filter(Pojo::hasProperty)
            .forEach(pojo -> pojo.generateAndSave(this.targetDirectory));
    }
}
