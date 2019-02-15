package com.github.uuidcode.builder.pojo;

import java.util.Map;
import java.util.stream.Collectors;

import com.github.uuidcode.util.CoreUtil;
import com.github.uuidcode.util.StringStream;

import static com.github.uuidcode.util.CoreUtil.SPACE4;
import static com.github.uuidcode.util.CoreUtil.templateInline;
import static com.github.uuidcode.util.StringStream.LINE_FEED;

public class Property {
    private String name;
    private Object value;
    private String type;
    private String className;

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

    public String getGetMethodName() {
        return "get" + CoreUtil.toFirstCharUpperCase(this.name);
    }

    public String getSetMethodName() {
        return "set" + CoreUtil.toFirstCharUpperCase(this.name);
    }

    public String createSetMethod(String className) {
        this.setClassName(className);

        String template = StringStream.of()
            .add("public {{className}} {{setMethodName}}({{field}}) {")
            .add("    this.{{name}} = {{name}};")
            .add("    return this;")
            .add("}")
            .map(CoreUtil::appendSpaces4)
            .joiningWithLineFeed();

        return CoreUtil.templateInline(template, this);
    }

    public String createGetMethod() {
        String template = StringStream.of()
            .add("public {{type}} {{getMethodName}}() {")
            .add("    return this.{{name}};")
            .add("}")
            .map(CoreUtil::appendSpaces4)
            .joiningWithLineFeed();

        return CoreUtil.templateInline(template, this);
    }

    public String createSetGetMethod(String className) {
        return StringStream.of()
            .addEmpty()
            .add(this.createSetMethod(className))
            .addEmpty()
            .add(this.createGetMethod())
            .joiningWithLineFeed();
    }

    public String createField() {
        String template = SPACE4 + "private {{type}} {{name}}";
        return CoreUtil.templateInline(template, this);
    }
}
