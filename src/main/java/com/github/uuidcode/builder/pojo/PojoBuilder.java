package com.github.uuidcode.builder.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.uuidcode.util.CoreUtil;

public class PojoBuilder {
    private String json;

    public static Map<String, String> typeConverter = new HashMap<String, String>() {{
        this.put("issues", "issue");
    }};

    public static String getJavaType(String name) {
        if (name.endsWith("List")) {
            name = CoreUtil.toFirstCharUpperCase(name.substring(0, name.length() - 4));
        }

        String type = typeConverter.getOrDefault(name, name);
        return CoreUtil.toFirstCharUpperCase(type);
    }

    public String getJson() {
        return this.json;
    }

    public PojoBuilder setJson(String json) {
        this.json = json;
        return this;
    }

    public static PojoBuilder of(String json) {
        return of().setJson(json);
    }

    public static PojoBuilder of() {
        return new PojoBuilder();
    }

    public Set<Entry<String, Object>> getEntrySet() {
        Map<String, Object> map = CoreUtil.fromJsonToMap(this.json);
        return map.entrySet();
    }


    public List<Property> getPropertyList() {
        Set<Entry<String, Object>> entrySet = this.getEntrySet();
        return this.getPropertyList(entrySet);
    }

    public List<Property> getPropertyList(Set<Entry<String, Object>> entrySet) {
        return entrySet.stream()
            .map(Property::of)
            .collect(Collectors.toList());
    }

    public Pojo getPojo() {
        List<Property> propertyList = this.getPropertyList();
        return Pojo.of().setPropertyList(propertyList);
    }

    public Pojo getPojo(Set<Entry<String, Object>> entrySet) {
        List<Property> propertyList = this.getPropertyList(entrySet);
        return Pojo.of().setPropertyList(propertyList);
    }

    public Pojo getPojo(Map<String, Object> map) {
        List<Property> propertyList = this.getPropertyList(map.entrySet());
        return Pojo.of().setPropertyList(propertyList);
    }

    public Pojo getPojo(List<Property> propertyList) {
        return Pojo.of().setPropertyList(propertyList);
    }
}
