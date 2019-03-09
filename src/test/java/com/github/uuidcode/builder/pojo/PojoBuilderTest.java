package com.github.uuidcode.builder.pojo;

import org.junit.Test;

import static com.github.uuidcode.util.CoreUtil.getContentFromResource;

public class PojoBuilderTest {
    @Test
    public void json() {
        PojoBuilder.of()
            .setPackageName("com.test.java.domain")
            .setClassName("Person")
            .setTargetDirectory(".test")
            .addNameConvert("issues", "issue")
            .addExcludeField("itemList")
            .setJson(getContentFromResource("pojo/payload.json"))
            .build();
    }

    @Test
    public void schema() {
        PojoBuilder.of()
            .setPackageName("com.test.java.domain")
            .setClassName("Payload")
            .setTargetDirectory(".test")
            .setSchema(getContentFromResource("pojo/schema.sql"))
            .build();
    }
}