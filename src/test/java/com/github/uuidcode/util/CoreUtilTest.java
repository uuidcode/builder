package com.github.uuidcode.util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;

import static com.github.uuidcode.util.CoreUtil.toJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class CoreUtilTest {
    protected static Logger logger = getLogger(CoreUtilTest.class);

    @Test
    public void test() {
        String clipboard = CoreUtil.getClipboard();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> test clipboard: {}", toJson(clipboard));
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
    
    @Test
    public void parseInteger() {
        Integer x = CoreUtil.parseInt(null);
        
        if (logger.isDebugEnabled()) {
            logger.debug(">>> parseInteger x: {}", toJson(x));
        }
    }
    
    @Test
    public void split() {
        List<String> list = CoreUtil.splitList("abc", "");
        
        if (logger.isDebugEnabled()) {
            logger.debug(">>> split list: {}", toJson(list));
        }
    }

    @Test
    public void getDayOfWeek() {
        int dayOfWeek = new DateTime().getDayOfWeek();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> getDayOfWeek dayOfWeek: {}", toJson(dayOfWeek));
        }
    }

    @Test
    public void getNextWednesday() {
        for (int i = 0; i < 10; i++) {
            DateTime dateTime = new DateTime().plusDays(i);
            Date date = CoreUtil.getNextWednesday(dateTime);

            if (logger.isDebugEnabled()) {
                logger.debug(">>> getNextWednesday date: {} {}", toJson(dateTime.toDate()), toJson(date));
            }
        }
    }

    public static <T> T clone(T t) {
        if (t == null) {
            return null;
        }

        try {
            if (t instanceof Serializable) {
                return (T) SerializationUtils.clone((Serializable) t);
            } else {
                throw new RuntimeException(t + " is not Serializable.");
            }
        } catch (Exception e) {
            logger.error("error", e);
        }

        return null;
    }
}