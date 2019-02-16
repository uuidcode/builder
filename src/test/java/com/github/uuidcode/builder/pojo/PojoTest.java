package com.github.uuidcode.builder.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;

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
}