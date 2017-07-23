package uuidcode.builder.html;

import static uuidcode.builder.html.HtmlBuilder.div;
import static uuidcode.builder.html.HtmlBuilder.input;
import static uuidcode.builder.html.HtmlBuilder.li;
import static uuidcode.builder.html.HtmlBuilder.script;
import static uuidcode.builder.html.HtmlBuilder.span;
import static uuidcode.builder.html.HtmlBuilder.text;
import static uuidcode.builder.html.HtmlBuilder.ul;
import static uuidcode.builder.html.HtmlBuilder.a;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class HtmlBuilderTest extends CoreTest {
    @Test
    public void test() {
        new HtmlBuilder();
        assertHtml(div(text("abc")).html(), "div");
    }

    @Test
    public void ul_2li() {
        String html = ul().add(li())
            .add(li())
            .html();

        assertHtml(html, "ul_2li");
    }

    @Test
    public void ul_li() {
        String html = ul().add(li())
            .html();

        assertHtml(html, "ul_li");
    }

    @Test
    public void div_2ul_li() {
        String html = div().add(ul().add(li()).add(li()))
            .add(ul().add(li()))
            .html();

        assertHtml(html, "div_2ul_li");
    }

    @Test
    public void complex() {
        String html =
        div(
            ul(
                li(
                    a()
                ),
                li(
                    div(
                        a(),
                        a()
                    )
                )
            ),
            ul(
                li(
                    a()
                )
            )
        ).html();

        assertHtml(html, "complex");
    }

    @Test
    public void selectBox() {
        List<String> nameList = IntStream.range(0, 3)
            .mapToObj(String::valueOf)
            .collect(Collectors.toList());

        Div div = div(
            input(),
            a(
                text("하트"),
                span().addClass("ico_comm")
            ),
            ul(this.createLiList(nameList)),
            script(text("var i = 'Hello, World!';"),
                text("console.log(i);"))
        );

        div.setId("projectTypeContainer").addClass("opt_comm4");
        div.getChildNodeList().get(0)
            .setType("hidden")
            .setName("projectType")
            .setId("projectType")
            .setValue("HEART");

        div.getChildNodeList().get(1).setId("projectTypeLabel")
            .addClass("link_selected");
        div.getChildNodeList().get(2).addClass("list_opt");

        this.assertHtml(div.html(), "selectBox");
    }

    private List<Node> createLiList(List<String> nameList) {
        return nameList.stream()
            .map(i -> a(text(i)).setId("type_" + i)
                    .setHref("http://www.google.com?q=" + i))
            .map(a -> li(a))
            .collect(Collectors.toList());
    }
}
