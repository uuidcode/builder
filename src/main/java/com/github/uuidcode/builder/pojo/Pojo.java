package com.github.uuidcode.builder.pojo;

import java.util.List;

import com.github.uuidcode.util.CoreUtil;

public class Pojo {
    private List<Property> propertyList;
    private String className;

    public String getClassName() {
        return this.className;
    }

    public Pojo setClassName(String className) {
        this.className = className;
        return this;
    }

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
        return CoreUtil.template("pojo", this);
    }
}
