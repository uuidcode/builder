package com.github.uuidcode.builder.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class PojoBuilder {
    protected static Logger logger = getLogger(PojoBuilder.class);

    public static Map<String, String> nameConverterMap = new HashMap<>();
    public static Set<String> includedFieldSet = new HashSet<>();
    public static Set<String> excludedFieldSet = new HashSet<>();

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

        if (logger.isDebugEnabled()) {
            logger.debug(">>> isAvailableField includedFieldSet: {}", CoreUtil.toJson(includedFieldSet));
            logger.debug(">>> isAvailableField excludedFieldSet: {}", CoreUtil.toJson(excludedFieldSet));
            logger.debug(">>> isAvailableField name: {}", CoreUtil.toJson(name));
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
                if (logger.isDebugEnabled()) {
                    logger.debug(">>> setSchema line: {}", CoreUtil.toJson(line));
                }

                line = line.trim();
                List<String> itemList = CoreUtil.splitListWithSpace(line);
                String type = itemList.get(1);

                if (type.startsWith("int")) {
                    type = Property.TYPE_LONG;
                } else if (type.startsWith("bigint")) {
                    type = Property.TYPE_LONG;
                } else if (type.startsWith("varbinary")) {
                    type = Property.TYPE_STRING;
                } else if (type.startsWith("enum")) {
                    type = Property.TYPE_STRING;
                } else if (type.startsWith("varchar")) {
                    type = Property.TYPE_STRING;
                } else if (type.startsWith("char")) {
                    type = Property.TYPE_STRING;
                } else if (type.startsWith("timestamp")) {
                    type = Property.TYPE_DATE;
                } else {
                    type = Property.TYPE_LONG;
                }

                return Property.of()
                    .setName(itemList.get(0).replaceAll("`", ""))
                    .setType(type)
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
        if (logger.isDebugEnabled()) {
            logger.debug(">>> build propertyList: {}", CoreUtil.toJson(propertyList));
        }

        Pojo.of()
            .setClassName(this.className)
            .setMap(this.map)
            .setPropertyList(this.propertyList)
            .build()
            .forEach(pojo -> pojo.generateAndSave(this.targetDirectory));
    }
}
