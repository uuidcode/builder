package com.github.uuidcode.builder.pojo;

import org.junit.Test;

import com.github.uuidcode.util.CoreUtil;

public class PojoBuilderTest {
    @Test
    public void test() {
        PojoBuilder.of()
            .setClassName("Person")
            .setJson(PojoTest.getJson())
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
            .setSchema(CoreUtil.getClipboard())
            .setTargetDirectory("test")
            .build();
    }
}