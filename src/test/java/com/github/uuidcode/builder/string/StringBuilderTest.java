package com.github.uuidcode.builder.string;

import static com.github.uuidcode.builder.string.StringBuilder.NEW_LINE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class StringBuilderTest {
    @Test
    public void of() {
        String result = StringBuilder.of().add("1").add("2").joining();
        assertThat(result).isEqualTo("12");

        result = StringBuilder.of("1").add("2").joining();
        assertThat(result).isEqualTo("12");

        result = StringBuilder.of(Arrays.asList("1", "2")).joining();
        assertThat(result).isEqualTo("12");

        result = StringBuilder.of(Arrays.asList(1, 2), String::valueOf).joining();
        assertThat(result).isEqualTo("12");
    }

    @Test
    public void add() {
        String result = StringBuilder.of()
            .add(false, "0")
            .add("1")
            .add("2")
            .joining();

        assertThat(result).isEqualTo("12");

        result = StringBuilder.of()
            .add("1")
            .add(() -> "2")
            .add(false, () -> "3")
            .joining();

        assertThat(result).isEqualTo("12");

        assertThat(StringBuilder.of("1").addSpace().joining()).isEqualTo("1 ");
        assertThat(StringBuilder.of("1").addNewLine().joining()).isEqualTo("1" + NEW_LINE);
        assertThat(StringBuilder.of("1").addSlash().joining()).isEqualTo("1/");
        assertThat(StringBuilder.of("1").addComma().joining()).isEqualTo("1,");
        assertThat(StringBuilder.of("1").addCommaAndSpace().joining()).isEqualTo("1, ");
        assertThat(StringBuilder.of("1").addAmpersand().joining()).isEqualTo("1&");
        assertThat(StringBuilder.of("1").addDot().joining()).isEqualTo("1.");
        assertThat(StringBuilder.of("1").addVerticalBar().joining()).isEqualTo("1|");
        assertThat(StringBuilder.of("1").addHyphen().joining()).isEqualTo("1-");
        assertThat(StringBuilder.of("1").addUnderscore().joining()).isEqualTo("1_");
    }

    @Test
    public void map() {
        String result = StringBuilder.of()
            .add("a")
            .add("ab")
            .map(i -> String.valueOf(i.length()))
            .joining();

        assertThat(result).isEqualTo("12");
    }

    @Test
    public void filter() {
        String result = StringBuilder.of()
            .add("a")
            .add("ab")
            .add("abc")
            .map(i -> String.valueOf(i.length()))
            .filter(i -> !i.equals("3"))
            .joining();

        assertThat(result).isEqualTo("12");
    }

    @Test
    public void skip() {
        String result = StringBuilder.of()
            .add("0")
            .add("1")
            .add("2")
            .skip(1L)
            .joining();

        assertThat(result).isEqualTo("12");
    }

    @Test
    public void joining() {
        assertThat(StringBuilder.of().add("1").add("2").joining())
            .isEqualTo("12");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithComma()).isEqualTo("1,2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithCommaAndSpace()).isEqualTo("1, 2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithCommaAndNewLine()).isEqualTo("1," + NEW_LINE + "2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithSpace()).isEqualTo("1 2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithUnderscore()).isEqualTo("1_2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithHyphen()).isEqualTo("1-2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithVerticalBar()).isEqualTo("1|2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithSlash()).isEqualTo("1/2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithQuestionMark()).isEqualTo("1?2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithEqual()).isEqualTo("1=2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithDot()).isEqualTo("1.2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithAmpersand()).isEqualTo("1&2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningWithNewLine()).isEqualTo("1" + NEW_LINE + "2");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningAndThen().add("3").joining()).isEqualTo("123");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningCommaAndThen().add("3").joining()).isEqualTo("1,23");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningCommaAndSpaceAndThen().add("3").joining()).isEqualTo("1, 23");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningSpaceAndThen().add("3").joining()).isEqualTo("1 23");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningUnderscoreAndThen().add("3").joining()).isEqualTo("1_23");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningHyphenAndThen().add("3").joining()).isEqualTo("1-23");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningVerticalBarAndThen().add("3").joining()).isEqualTo("1|23");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningSlashAndThen().add("3").joining()).isEqualTo("1/23");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningQuestionMarkAndThen().add("3").joining()).isEqualTo("1?23");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningDotAndThen().add("3").joining()).isEqualTo("1.23");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningAmpersandAndThen().add("3").joining()).isEqualTo("1&23");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningNewLineAndThen().add("3").joining()).isEqualTo("1" + NEW_LINE + "23");

        assertThat(StringBuilder.of().add("1").add("2")
            .joiningEqualAndThen().add("3").joining()).isEqualTo("1=23");
    }

    @Test
    public void url() {
        String url = StringBuilder.of("")
            .add("project")
            .add("stage")
            .add("1024")
            .joiningSlashAndThen()
            .addQuestionMark()
            .add("mode")
            .addEqual()
            .add("test")
            .addAmpersand()
            .add("size")
            .addEqual()
            .add(3)
            .joining();
        
        assertThat(url).isEqualTo("/project/stage/1024?mode=test&size=3");
    }
}