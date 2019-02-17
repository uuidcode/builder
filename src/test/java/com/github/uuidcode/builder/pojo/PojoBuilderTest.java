package com.github.uuidcode.builder.pojo;

import org.junit.Test;

public class PojoBuilderTest {
    @Test
    public void test() {
        PojoBuilder.of()
            .setClassName("Person")
            .setJson(PojoTest.getJson())
            .setTargetDirectory("test")
            .addNameConvert("issues", "issue")
            .addExcludeField("itemList")
            .buildPojo();
    }
}