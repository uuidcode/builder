package com.github.uuidcode.builder.pojo;

import java.util.Map;

import com.github.uuidcode.util.CoreUtil;

public class Property {
    private String name;
    private Object value;
    private PropertyType propertyType;
    private String className;

    public boolean isDate() {
        return this.propertyType == PropertyType.DATE;
    }

    public String getJavaType() {
        return this.propertyType.getJavaType();
    }

    public String getClassName() {
        return this.className;
    }

    public Property setClassName(String className) {
        this.className = className;
        return this;
    }

    public static Property of() {
        return new Property();
    }

    public static Property of(Map.Entry<String, Object> entry) {
        return Property.of()
            .setName(entry.getKey())
            .setValue(entry.getValue())
            .setPropertyType(getType(entry.getValue()));
    }

    public static PropertyType getType(Object object) {
        if (object == null) {
            return PropertyType.STRING;
        }

        String type = object.getClass().getSimpleName();

        PropertyType propertyType = CoreUtil.lookupEnum(PropertyType.class, type);

        if (propertyType == null) {
            if (Double.class.getSimpleName().equals(type)) {
                return PropertyType.LONG;
            }

            return PropertyType.STRING;
        }

        return propertyType;
    }

    public PropertyType getPropertyType() {
        return this.propertyType;
    }

    public Property setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
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

    public String getGetMethodName() {
        return "get" + CoreUtil.toFirstCharUpperCase(this.name);
    }

    public String getSetMethodName() {
        return "set" + CoreUtil.toFirstCharUpperCase(this.name);
    }
}
