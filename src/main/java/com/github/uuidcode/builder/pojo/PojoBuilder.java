package com.github.uuidcode.builder.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static com.github.uuidcode.builder.pojo.Property.TYPE_DATE;
import static com.github.uuidcode.builder.pojo.Property.TYPE_LONG;
import static com.github.uuidcode.builder.pojo.Property.TYPE_STRING;
import static com.github.uuidcode.util.CoreUtil.splitListWithSpace;
import static org.slf4j.LoggerFactory.getLogger;

public class PojoBuilder {
    protected static Logger logger = getLogger(PojoBuilder.class);

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
    private Map<String, Object> map;
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

    public PojoBuilder setJson(String json) {
        this.map = CoreUtil.fromJsonToMap(json);
        return this;
    }

    public PojoBuilder setSchema(String schema) {
        this.propertyList = CoreUtil.splitListWithNewLine(schema)
            .stream()
            .map(line -> {
                line = line.trim();
                List<String> itemList = splitListWithSpace(line);
                final String type = itemList.get(1);
                String convertedType = typeConvertMap.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().startsWith(type))
                    .findFirst()
                    .map(Entry::getValue)
                    .orElse(TYPE_STRING);

                return Property.of()
                    .setName(itemList.get(0).replaceAll("`", ""))
                    .setType(convertedType)
                    .setIsList(false);
            })
            .collect(Collectors.toList());

        return this;
    }

    public static String getJavaType(String name) {
        if (name.endsWith("List")) {
            name = CoreUtil.toFirstCharUpperCase(name.substring(0, name.length() - 4));
        }

        String type = nameConverterMap.getOrDefault(name, name);
        return CoreUtil.toFirstCharUpperCase(type);
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
        Pojo.of()
            .setPackageName(this.packageName)
            .setClassName(this.className)
            .setMap(this.map)
            .setPropertyList(this.propertyList)
            .build()
            .forEach(pojo -> pojo.generateAndSave(this.targetDirectory));
    }
}
