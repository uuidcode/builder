package com.github.uuidcode.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;

import com.github.uuidcode.builder.domain.Person;

import static com.github.uuidcode.util.CoreUtil.toJson;
import static java.util.Optional.ofNullable;
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

    public void getNextDayOfWeek(Function<DateTime, Date> mapper) {
        for (int i = 0; i < 10; i++) {
            DateTime dateTime = new DateTime().plusDays(i);
            Date date = mapper.apply(dateTime);

            if (logger.isDebugEnabled()) {
                logger.debug(">>> getNextDayOfWeek date: {} {}", toJson(dateTime.toDate()), toJson(date));
            }
        }
    }
    @Test
    public void getNextDayOfWeek() {
        if (logger.isDebugEnabled()) {
            logger.debug(">>> getNextMonday");
        }

        this.getNextDayOfWeek(CoreUtil::getNextMonday);

        if (logger.isDebugEnabled()) {
            logger.debug(">>> getNextTuesday");
        }

        this.getNextDayOfWeek(CoreUtil::getNextTuesday);

        if (logger.isDebugEnabled()) {
            logger.debug(">>> getNextWednesday");
        }

        this.getNextDayOfWeek(CoreUtil::getNextWednesday);

        if (logger.isDebugEnabled()) {
            logger.debug(">>> getNextThursday");
        }

        this.getNextDayOfWeek(CoreUtil::getNextThursday);

        if (logger.isDebugEnabled()) {
            logger.debug(">>> getNextFriday");
        }

        this.getNextDayOfWeek(CoreUtil::getNextFriday);

        if (logger.isDebugEnabled()) {
            logger.debug(">>> getNextSaturday");
        }

        this.getNextDayOfWeek(CoreUtil::getNextSaturday);

        if (logger.isDebugEnabled()) {
            logger.debug(">>> getNextSunday");
        }

        this.getNextDayOfWeek(CoreUtil::getNextSunday);
    }

    @Test
    public void toPredicateTest() {
        List<Person> list = new ArrayList<>();
        list.add(Person.of().setName("1"));
        list.add(Person.of().setName("2"));

        list.stream()
            .filter(person -> Objects.equals(person.getName(), "1"))
            .forEach(System.out::println);

        list.stream()
            .filter(person -> person.isSameName("1"))
            .forEach(System.out::println);

        list.stream()
            .filter(Person.sameName("1"))
            .forEach(System.out::println);
    }

    @Test
    public void equals() {
        assertThat(Objects.equals(null, null)).isTrue();
    }

    @Test
    public void predicateEqualsTest() {
        {
            Person p = null;
            Person person = ofNullable(p)
                .filter(CoreUtil.equals(Person::getName, "hello"))
                .orElse(Person.of())
                .assertName(null);
        }

        {
            Person p = Person.of().setName("hello");
            Person person = ofNullable(p)
                .filter(CoreUtil.equals(Person::getName, "hello"))
                .orElse(Person.of())
                .assertName("hello");
        }
    }

    @Test
    public void escapeSingleQuotation() {
        String result = CoreUtil.escapeSingleQuotation("<pre><code>BRUNCH-8889 [운영툴] 운영 권한 그룹 중 'dks_call' 일 때 메뉴 노출 예외 처리");
        assertThat(result).isEqualTo("<pre><code>BRUNCH-8889 [운영툴] 운영 권한 그룹 중 &apos;dks_call&apos; 일 때 메뉴 노출 예외 처리");
    }
}