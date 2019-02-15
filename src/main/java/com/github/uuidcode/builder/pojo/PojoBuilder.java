package com.github.uuidcode.builder.pojo;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.uuidcode.util.CoreUtil;

public class PojoBuilder {
    private String json;

    public String getJson() {
        return this.json;
    }

    public PojoBuilder setJson(String json) {
        this.json = json;
        return this;
    }

    public static PojoBuilder of(String json) {
        return new PojoBuilder().setJson(json);
    }

    public Set<Entry<String, Object>> getEntrySet() {
        Map<String, Object> map = CoreUtil.fromJsonToMap(this.json);
        return this.getEntrySet(map);
    }

    public Set<Entry<String, Object>> getEntrySet(Map<String, Object> map) {
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

    public Pojo getPojo(List<Property> propertyList) {
        return Pojo.of().setPropertyList(propertyList);
    }
}
