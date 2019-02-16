package com.github.uuidcode.builder.pojo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.slf4j.Logger;

import com.github.uuidcode.util.StringStream;

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

    @Test
    public void build() {
        String json = StringStream.of()
            .add("{")
            .add("abc: '123',")
            .add("def: 123, ")
            .add("created: '2019-01-01',")
            .add("person: {name: 'hello', items: [{id: 37}]},")
            .add("issues: [{id: 33, created: '2019-01-01', book: {name: 'hello', empty: false, pageList: [{id: 37}]}}]")
            .add("}")
            .joiningWithLineFeed()
            .replaceAll("'", "\"");

        String content = PojoBuilder.of(json)
            .getPojo()
            .setClassName("Test")
            .generate();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> build content: {}", content);
        }
    }
}