package com.github.uuidcode.builder.html;

import org.junit.Test;

public class LiTest extends CoreTest {
    @Test
    public void test() {
        assertHtml(Li.of().html(), "li");
    }
}