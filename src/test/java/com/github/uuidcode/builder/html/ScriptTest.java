package com.github.uuidcode.builder.html;

import static com.github.uuidcode.builder.html.HtmlBuilder.text;

import org.junit.Test;

public class ScriptTest extends CoreTest{
    @Test
    public void test() {
        String html = Script.of(
            text("var i = 'Hello, World!';"),
            text("console.log(i);")
        ).html();

        this.assertHtml(html, "script");
    }

}