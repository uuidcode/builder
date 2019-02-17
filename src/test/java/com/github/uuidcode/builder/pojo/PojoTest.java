package com.github.uuidcode.builder.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;
import com.github.uuidcode.util.StringStream;

import static org.slf4j.LoggerFactory.getLogger;

public class PojoTest {
    protected static Logger logger = getLogger(PojoTest.class);

    @Test
    public void test() {
        List<Property> propertyList = new ArrayList<Property>() {{
            this.add(Property.of()
                .setPropertyType(Long.class.getSimpleName())
                .setName("name")
                .setValue("Hello"));

            this.add(Property.of()
                .setPropertyType(String.class.getSimpleName())
                .setName("title")
                .setValue("world"));

            this.add(Property.of()
                .setPropertyType(Date.class.getSimpleName())
                .setName("createDate")
                .setValue(new Date()));
        }};

        String content = Pojo.of()
            .setPropertyList(propertyList)
            .setClassName("Test")
            .generate();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> test content: \n{}", content);
        }
    }

    @Test
    public void of() {
        String json = StringStream.of()
            .add("{")
            .add("name: \"ted\",")
            .add("age: 35,")
            .add("birthDate: 2019-01-01,")
            .add("itemList: [1,2],")
            .add("issues: [{id: 3, typeList: [{token: \"333\"}]}, {id: 11, typeList: [{token: \"777\"}]}]")
            .add("}")
            .joining();

        List<Pojo> pojoList = Pojo.of("Person", json);

        if (logger.isDebugEnabled()) {
            logger.debug(">>> of pojoList: {}", CoreUtil.toJson(pojoList));
        }

        pojoList.forEach(pojo -> {
            if (logger.isDebugEnabled()) {
                logger.debug(">>> of pojo: {}", pojo.generate());
            }
        });
    }
}