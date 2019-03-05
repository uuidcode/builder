package com.github.uuidcode.util;

import org.junit.Test;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void multipleEmptyLineToOneEmptyLine() {
        {
            String actual = CoreUtil.multipleEmptyLineToOneEmptyLine("1\n\n\n\n2");
            assertThat(actual).isEqualTo("1\n\n2");
        }

        {
            String actual = CoreUtil.multipleEmptyLineToOneEmptyLine("1\n\n\n2");
            assertThat(actual).isEqualTo("1\n\n2");
        }

        {
            String actual = CoreUtil.multipleEmptyLineToOneEmptyLine("1\n\n2");
            assertThat(actual).isEqualTo("1\n\n2");
        }

    }
}