package com.github.uuidcode.builder.html;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AttributeTest {

    @Test
    public void test() {
        assertThat(Attribute.of("disabled").toString())
            .isEqualTo("disabled");

        Attribute attribute = Attribute.of("id", "ABC");

        assertThat(attribute.toString())
            .isEqualTo("id=\"ABC\"");

        assertThat(attribute.getName()).isEqualTo("id");

        String[] values = attribute.getValueSet().toArray(new String[0]);
        assertThat(values).hasSize(1);
        assertThat(values[0]).isEqualTo("ABC");
    }
}