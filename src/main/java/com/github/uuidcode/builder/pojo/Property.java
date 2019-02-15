package com.github.uuidcode.builder.pojo;

import java.util.Map;

public class Property {
    private String name;
    private Object value;
    private String type;

    public static Property of(Map.Entry<String, Object> entry) {
        Property property = new Property()
            .setName(entry.getKey())
            .setValue(entry.getValue())
            .setType(getType(entry.getValue()));

        if (Double.class.getSimpleName().equals(property.getType())) {
            property.setType(Long.class.getSimpleName());
            property.setValue(((Double) property.getValue()).longValue());
        }

        return property;
    }

    public static String getType(Object object) {
        if (object == null) {
            return String.class.getSimpleName();
        }

        return object.getClass().getSimpleName();
    }

    public String getType() {
        return this.type;
    }

    public Property setType(String type) {
        this.type = type;
        return this;
    }
    public Object getValue() {
        return this.value;
    }

    public Property setValue(Object value) {
        this.value = value;
        return this;
    }
    public String getName() {
        return this.name;
    }

    public Property setName(String name) {
        this.name = name;
        return this;
    }
}
