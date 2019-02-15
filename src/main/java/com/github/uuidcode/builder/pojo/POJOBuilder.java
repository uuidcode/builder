package com.github.uuidcode.builder.pojo;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.uuidcode.util.CoreUtil;

public class POJOBuilder {
    private String json;

    public String getJson() {
        return this.json;
    }

    public POJOBuilder setJson(String json) {
        this.json = json;
        return this;
    }

    public static POJOBuilder of(String json) {
        return new POJOBuilder().setJson(json);
    }

    public Set<Entry<String, Object>> getEntrySet() {
        Map<String, Object> map = (Map<String, Object>) CoreUtil.fromJsonToMap(this.json);
        return map.entrySet();
    }
}
