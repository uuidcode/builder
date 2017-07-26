package com.github.uuidcode.builder.html;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class InputTest extends CoreTest {
    @Test
    public void test() {
        assertThat(Input.of().getRequiresEndTag()).isFalse();

        String html = Input.of()
            .setType("text")
            .setName("phone")
            .setValue("000-1111-2222")
            .setDisabled(true)
            .html();

        this.assertHtml(html, "input");
    }
}