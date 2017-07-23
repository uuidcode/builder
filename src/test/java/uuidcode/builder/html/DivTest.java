package uuidcode.builder.html;

import org.junit.Test;

public class DivTest extends CoreTest {
    @Test
    public void test() {
        assertHtml(Div.of().add(Text.of("abc")).html(), "div");
    }

    @Test
    public void addClass() {
        String html = Div.of()
            .addClass("list")
            .addClass("list")
            .addClass("primary")
            .addStyle("color:red;")
            .addStyle("color:red;")
            .addStyle("border:1px solid blue;")
            .addStyle("border:1px solid blue;")
            .html();

        assertHtml(html, "div_class");
    }
}