package uuidcode.builder.html;

import static org.junit.Assert.*;

import org.junit.Test;

public class ATest extends CoreTest {
    @Test
    public void test() {
        assertHtml(A.of().html(), "a");
    }

}