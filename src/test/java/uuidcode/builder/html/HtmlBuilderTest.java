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
        assertHtml(div().html(), "div");
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
            ul(
                nameList.stream()
                    .map(i -> li(a()).setId(i))
                    .collect(Collectors.toList())
            ),
            script(text("Hello, World"))
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

        String html = div.html();

        this.assertHtml(html, "selectBox");
    }
}
