package com.github.uuidcode.builder.jquery;

import static com.github.uuidcode.builder.jquery.JQueryBuilder.$;

import org.junit.Test;

import com.github.uuidcode.builder.html.CoreTest;

public class JQueryBuilderTest extends CoreTest {
    @Test
    public void test() {
        String javascript = $("button.continue").html("Hello, World!").generate();
        this.assertJs(javascript, "html");
    }
}