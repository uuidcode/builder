package uuidcode.builder.html;

import org.junit.Test;

public class DivTest extends CoreTest {
    @Test
    public void test() {
        assertHtml(Div.of().html(), "div");
    }
}