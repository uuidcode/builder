package uuidcode.builder.html;

import static org.assertj.core.api.Assertions.assertThat;
import static uuidcode.builder.html.HtmlBuilder.li;
import static uuidcode.builder.html.HtmlBuilder.ul;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class NodeTest {
    @Test
    public void test() {
        Node<Node> node = new Node<>();
        node.setTagName("abc");
        List<String> contentList = node.contentList();
        AssertList.of(contentList)
            .hasSize(1)
            .satisfies(0, i -> i.equals("<abc></abc>"));
    }

    @Test
    public void add() {
        Ul ul = ul();
        Li li = li();
        ul.add(li);
        assertThat(li.getParentNode()).isEqualTo(ul);
    }

    @Test
    public void addNull() {
        Node node = null;
        AssertList.of(ul().add(node).getChildNodeList()).hasSize(0);
    }

    @Test
    public void shrink() {
        List<String> list = Arrays.asList("1", "2", "3");
        Node node = new Node();
        AssertList.of(node.shrink(list))
            .hasSize(1)
            .satisfies(0, i -> i.equals("123"));

        list = Arrays.asList("1", "2", "3", "4");
        AssertList.of(node.shrink(list))
            .hasSize(4)
            .satisfies(0, i -> i.equals("1"))
            .satisfies(1, i -> i.equals("2"))
            .satisfies(2, i -> i.equals("3"))
            .satisfies(3, i -> i.equals("4"));
    }
}