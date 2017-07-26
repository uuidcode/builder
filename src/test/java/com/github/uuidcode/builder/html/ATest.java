package com.github.uuidcode.builder.html;

import org.junit.Test;

public class ATest extends CoreTest {
    @Test
    public void test() {
        assertHtml(A.of().html(), "a");
    }

}