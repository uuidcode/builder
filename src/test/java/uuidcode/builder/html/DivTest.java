package uuidcode.builder.html;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DivTest extends CoreTest {
    @Test
    public void test() {
        assertHtml(Div.of().html(), "div");
    }
}