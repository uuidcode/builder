package com.github.uuidcode.builder.pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.uuidcode.util.CoreUtil;

public class Property {
    private String name;
    private Object value;
    private String propertyType;
    private String className;

    public boolean isDate() {
        return this.propertyType.equals("Date");
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
        Property property = Property.of()
            .setName(entry.getKey())
            .setValue(entry.getValue());

        String type = getType(property);

        return property.setPropertyType(type);
    }

    public static String getType(Property property) {
        Object object = property.getValue();
        String name = property.getName();

        if (object == null) {
            return String.class.getSimpleName();
        }

        if (object instanceof String) {
            Date date = CoreUtil.parseDateTime(object.toString());

            if (date != null) {
                return Date.class.getSimpleName();
            }

            return String.class.getSimpleName();
        } else if (object instanceof Boolean) {
            return Boolean.class.getSimpleName();
        } else if (object instanceof Double) {
            return Long.class.getSimpleName();
        } else if (object instanceof Map) {
            String javaType = PojoBuilder.getJavaType(name);

            String content = PojoBuilder.of()
                .getPojo((Map<String, Object>) object)
                .setClassName(javaType)
                .generate();

            System.out.println(content);

            return javaType;
        } else if (object instanceof List) {
            return getListType((List) object, name);
        }

        throw new RuntimeException("not support type: " + object.getClass().getName());
    }

    public static String getListType(List object, String name) {
        Object itemObject = null;

        if (object.size() > 0) {
            itemObject = object.get(0);
        }

        Property itemProperty = Property.of()
            .setName(name)
            .setValue(itemObject);

        String itemType = getType(itemProperty);
        itemType = PojoBuilder.getJavaType(itemType);

        return "List<" + itemType + ">";
    }

    public String getPropertyType() {
        return this.propertyType;
    }

    public Property setPropertyType(String propertyType) {
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
