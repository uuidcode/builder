package com.github.uuidcode.builder.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class PojoBuilder {
    protected static Logger logger = getLogger(PojoBuilder.class);

    public static Map<String, String> nameConverterMap = new HashMap<>();
    public static Set<String> includedFieldSet = new HashSet<>();
    public static Set<String> excludedFieldSet = new HashSet<>();

    private String json;
    private String className;
    private String targetDirectory;
    private String packageName;

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

    public static boolean isAvailableField(Entry<String, Object> entry) {
        if (includedFieldSet.isEmpty() && excludedFieldSet.isEmpty()) {
            return true;
        }

        String name = entry.getKey();

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

    public String getJson() {
        return this.json;
    }

    public PojoBuilder setJson(String json) {
        this.json = json;
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

    public void buildPojo() {
        Pojo.of()
            .setClassName(this.className)
            .setMap(CoreUtil.fromJsonToMap(this.json))
            .build()
            .forEach(pojo -> pojo.generateAndSave(this.targetDirectory));
    }
}
