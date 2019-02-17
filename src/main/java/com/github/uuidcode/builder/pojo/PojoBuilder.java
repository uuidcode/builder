package com.github.uuidcode.builder.pojo;

import java.util.HashMap;
import java.util.Map;

import com.github.uuidcode.util.CoreUtil;

public class PojoBuilder {
    public static Map<String, String> typeConverter = new HashMap<String, String>() {{
        this.put("issues", "issue");
    }};

    public static String getJavaType(String name) {
        if (name.endsWith("List")) {
            name = CoreUtil.toFirstCharUpperCase(name.substring(0, name.length() - 4));
        }

        String type = typeConverter.getOrDefault(name, name);
        return CoreUtil.toFirstCharUpperCase(type);
    }

    public static PojoBuilder of(String json) {
        return new PojoBuilder();
    }
}
