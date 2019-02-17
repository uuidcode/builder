package com.github.uuidcode.util;

import org.junit.Test;
import org.slf4j.Logger;

import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

public class CoreUtilTest {
    protected static Logger logger = getLogger(CoreUtilTest.class);

    @Test
    public void test() {
        String clipboard = CoreUtil.getClipboard();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> test clipboard: {}", CoreUtil.toJson(clipboard));
        }
    }
}