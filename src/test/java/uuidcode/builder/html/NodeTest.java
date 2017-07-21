package uuidcode.builder.html;

import java.util.List;

import org.junit.Test;

public class NodeTest {
    @Test
    public void test() {
        Node node = new Node();
        node.setTagName("abc");
        List contentList = node.contentList();

        AssertList.of(contentList)
            .hasSize(2)
            .satisfies(0, i -> i.equals("<abc>"))
            .satisfies(1, i -> i.equals("</abc>"));
    }
}