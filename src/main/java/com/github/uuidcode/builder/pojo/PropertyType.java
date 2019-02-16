package com.github.uuidcode.builder.pojo;

import com.github.uuidcode.util.CoreUtil;

public enum PropertyType {
    STRING, LONG, DATE, BOOLEAN;

    public String getJavaType() {
        String lowerCase = this.name().toLowerCase();
        return CoreUtil.toFirstCharUpperCase(lowerCase);
    }
}
