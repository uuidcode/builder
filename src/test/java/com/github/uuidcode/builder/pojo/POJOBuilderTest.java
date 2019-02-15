package com.github.uuidcode.builder.pojo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.slf4j.Logger;

import static com.github.uuidcode.util.CoreUtil.toJson;
import static org.slf4j.LoggerFactory.getLogger;

public class POJOBuilderTest {
    protected static Logger logger = getLogger(POJOBuilderTest.class);

    @Test
    public void test() {
        Set<Map.Entry<String, Object>> entrySet =
            PojoBuilder.of("{abc: \"123\", def: 123}")
            .getEntrySet();

        List<Property> propertyList = entrySet.stream()
            .map(Property::of)
            .collect(Collectors.toList());

        if (logger.isDebugEnabled()) {
            logger.debug(">>> test propertyList: {}", toJson(propertyList));
        }
    }
}