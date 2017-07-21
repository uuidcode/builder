package uuidcode.builder.html;

import static uuidcode.builder.html.HtmlBuilder.div;

import org.junit.Test;

public class HtmlBuilderTest extends CoreTest {
    @Test
    public void test() {
        new HtmlBuilder();
        assertHtml(div().html(), "div");
    }
}
