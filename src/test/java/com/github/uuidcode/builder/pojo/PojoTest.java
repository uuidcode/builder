package com.github.uuidcode.builder.pojo;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;
import com.github.uuidcode.util.StringStream;

import static org.slf4j.LoggerFactory.getLogger;

public class PojoTest {
    protected static Logger logger = getLogger(PojoTest.class);

    @Test
    public void of() {
        String json = getJson();

        List<Pojo> pojoList = Pojo.of()
            .setClassName("Person")
            .setJson(getJson())
            .build();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> of pojoList: {}", CoreUtil.toJson(pojoList));
        }

        pojoList.forEach(pojo -> {
            if (logger.isDebugEnabled()) {
                logger.debug(">>> of pojo: {}", pojo.generate());
            }
        });
    }

    public static String getJson() {
        return StringStream.of()
                .add("{")
                .add("name: \"ted\",")
                .add("age: 35,")
                .add("birthDate: 2019-01-01,")
                .add("itemList: [1,2],")
                .add("issues: [{id: 3, typeList: [{token: \"333\"}]}, {id: 11, typeList: [{token: \"777\"}]}]")
                .add("}")
                .joining();
    }
}