package com.github.uuidcode.builder.html;

import org.junit.Test;

public class UlTest extends CoreTest {
    @Test
    public void test() {
        assertHtml(Ul.of().html(), "ul");
    }
}