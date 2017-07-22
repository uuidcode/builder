package uuidcode.builder.html;

import static org.assertj.core.api.Assertions.assertThat;
import static uuidcode.builder.html.Input.of;

import org.junit.Test;

public class InputTest {
    @Test
    public void test() {
        assertThat(of().getRequiresEndTag()).isFalse();
    }
}