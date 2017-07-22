package uuidcode.builder.html;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AttributeTest {

    @Test
    public void test() {
        assertThat(Attribute.of("disabled").toString())
            .isEqualTo("disabled");

        Attribute attribute = Attribute.of("id", "ABC");

        assertThat(attribute.toString())
            .isEqualTo("id=\"ABC\"");

        assertThat(attribute.getName()).isEqualTo("id");

        AssertList.of(attribute.getValueList())
            .hasSize(1)
            .satisfies(0, i -> i.equals("ABC"));
    }
}