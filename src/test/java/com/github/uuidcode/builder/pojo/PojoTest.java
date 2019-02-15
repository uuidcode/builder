package com.github.uuidcode.builder.pojo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static com.github.uuidcode.util.CoreUtil.asList;
import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

public class PojoTest {
    protected static Logger logger = getLogger(PojoTest.class);

    @Test
    public void test() {
        List<Property> propertyList = new ArrayList<Property>() {{
            this.add(Property.of()
                .setType("Long")
                .setName("name")
                .setValue("Hello"));

            this.add(Property.of()
                .setType("String")
                .setName("title")
                .setValue("world"));
        }};

        String content = Pojo.of()
            .setPropertyList(propertyList)
            .generate("Test");

        if (logger.isDebugEnabled()) {
            logger.debug(">>> test content: \n{}", content);
        }
    }

}