package com.github.uuidcode.builder.pojo;

import java.util.Map;
import java.util.Optional;

import com.github.uuidcode.util.CoreUtil;
import com.github.uuidcode.util.StringStream;

import static com.github.uuidcode.util.CoreUtil.underscoreToLowerCamel;

public class Property {
    public final static String TYPE_BOOLEAN = "Boolean";
    public final static String TYPE_STRING = "String";
    public final static String TYPE_LONG = "Long";
    public final static String TYPE_DATE = "Date";

    private String name;
    private Object value;
    private String type;
    private boolean isList;
    private boolean newType;
    private String className;

    public String getClassName() {
        return this.className;
    }

    public Property setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getJavaType() {
        if (this.isList) {
            return "List<type>".replaceAll("type", this.type);
        }

        return this.type;
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

    public Property setIsList() {
        return this.setIsList(true);
    }

    public boolean isDate() {
        return this.type.equals(TYPE_DATE);
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
        return PropertyTypeConverter.convert(property);
    }

    public String getType() {
        return this.type;
    }

    public Property setType(String type) {
        this.type = type;
        return this;
    }

    public Map<String, Object> getValueAsMap() {
        return (Map<String, Object>) this.value;
    }

    public Object getValue() {
        return this.value;
    }

    public Optional<Property> optional() {
        return Optional.of(this);
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

    public boolean isAvailable() {
        return PojoBuilder.isAvailableField(this.name);
    }

    public Property processName() {
        this.name = underscoreToLowerCamel(name);
        return this;
    }

    private String getFieldTemplate() {
        return StringStream.of()
            .add("    private type name;")
            .joining();
    }

    public String getFieldContent() {
        return this.getFieldTemplate()
            .replaceAll("type", this.getJavaType())
            .replaceAll("name", this.name);
    }

    private String getMethodTemplate() {
        return StringStream.of()
            .addEmpty()
            .add("    public class setMethod(type name) {")
            .add("        this.name = name;")
            .add("        return this;")
            .add("    }")
            .add("")
            .add("    public type getMethod() {")
            .add("        return this.name;")
            .add("    }")
            .joiningWithLineFeed();
    }

    public String getMethodContent() {
        return getMethodTemplate()
            .replaceAll("type", this.getJavaType())
            .replaceAll("class", this.className)
            .replaceAll("name", this.name)
            .replaceAll("setMethod", this.getSetMethodName())
            .replaceAll("getMethod", this.getGetMethodName())
            .replaceAll("type", this.getJavaType());
    }
}

