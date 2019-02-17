package com.github.uuidcode.builder.pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.uuidcode.util.CoreUtil;
import com.github.uuidcode.util.StringStream;

public class Property {
    private final static String TYPE_BOOLEAN = "Boolean";
    private final static String TYPE_STRING = "String";
    private final static String TYPE_LONG = "Long";
    private final static String TYPE_DATE = "Date";

    private String name;
    private Object value;
    private String propertyType;
    private String className;
    private boolean isList;
    private boolean newType;

    public String getJavaType() {
        return StringStream.of()
            .add(this.isList, "List<")
            .add(this.propertyType)
            .add(this.isList, ">")
            .joining();
    }

    public boolean getNewType() {
        return this.newType;
    }

    public Property setNewType(boolean newType) {
        this.newType = newType;
        return this;
    }

    public boolean getIsList() {
        return this.isList;
    }

    public Property setIsList(boolean isList) {
        this.isList = isList;
        return this;
    }

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

        return processType(property);
    }

    public static Property processType(Property property) {
        Object object = property.getValue();
        String name = property.getName();

        if (object == null) {
            return property.setPropertyType(TYPE_STRING);
        }

        if (object instanceof String) {
            Date date = CoreUtil.parseDateTime(object.toString());

            if (date != null) {
                return property.setPropertyType(TYPE_DATE);
            }

            return property.setPropertyType(TYPE_STRING);
        } else if (object instanceof Boolean) {
            return property.setPropertyType(TYPE_BOOLEAN);
        } else if (object instanceof Double) {
            return property.setPropertyType(TYPE_LONG);
        } else if (object instanceof Map) {
            return property.setPropertyType(PojoBuilder.getJavaType(name))
                .setNewType(true);
        } else if (object instanceof List) {
            return processListType(property);
        }

        throw new RuntimeException("not support type: " + object.getClass().getName());
    }

    public static Property processListType(Property property) {
        List object = (List) property.getValue();
        String name = property.getName();

        Object itemObject = null;

        if (object.size() > 0) {
            itemObject = object.get(0);
        }

        Property itemProperty = Property.of()
            .setName(name)
            .setValue(itemObject);

        return processType(itemProperty).setIsList(true);
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

