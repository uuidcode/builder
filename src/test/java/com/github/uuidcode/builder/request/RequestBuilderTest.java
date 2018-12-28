package com.github.uuidcode.builder.request;

import org.junit.Test;
import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;
import com.google.gson.FieldNamingPolicy;

import static org.slf4j.LoggerFactory.getLogger;

public class RequestBuilderTest {
    protected static Logger logger = getLogger(RequestBuilderTest.class);

    @Test
    public void test() {
        Result result = RequestBuilder.of().get("https://reqres.in/api/users?page=2")
            .setFieldNamePolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .executeAndGetObject(Result.class);

        if (logger.isDebugEnabled()) {
            logger.debug(">>> test result: {}", CoreUtil.toJson(result));
        }
    }
}