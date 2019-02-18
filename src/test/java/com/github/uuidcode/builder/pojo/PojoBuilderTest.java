package com.github.uuidcode.builder.pojo;

import org.junit.Test;

import com.github.uuidcode.util.CoreUtil;

import static com.github.uuidcode.util.CoreUtil.getContentFromResource;

public class PojoBuilderTest {
    @Test
    public void test() {
        PojoBuilder.of()
            .setPackageName("com.test.java.domain")
            .setClassName("Person")
            .setJson(CoreUtil.getContentFromResource("pojo/payload.json"))
            .setTargetDirectory("test")
            .addNameConvert("issues", "issue")
            .addExcludeField("itemList")
            .build();
    }

    @Test
    public void clipboard() {
        PojoBuilder.of()
            .setClassName("Payload")
            .setJson(CoreUtil.getClipboard())
            .setTargetDirectory("test")
            .build();
    }

    @Test
    public void db() {
        PojoBuilder.of()
            .setClassName("Payload")
            .setSchema(getContentFromResource("pojo/schema.sql"))
            .setTargetDirectory("test")
            .build();
    }
}