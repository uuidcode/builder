package com.github.uuidcode.builder.pojo;

import java.util.List;
import java.util.stream.Collectors;

import com.github.uuidcode.util.CoreUtil;
import com.github.uuidcode.util.StringStream;

import static com.github.uuidcode.util.CoreUtil.templateInline;

public class Pojo {
    private List<Property> propertyList;

    public static Pojo of() {
        return new Pojo();
    }

    public List<Property> getPropertyList() {
        return this.propertyList;
    }

    public Pojo setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
        return this;
    }

    public String generate() {
        String template = "private {{type}} {{name}}";

        return this.propertyList.stream()
            .map(property -> templateInline(template, property))
            .collect(Collectors.joining("\n"));
    }
}
