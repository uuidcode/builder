package com.github.uuidcode.builder.html;

import static com.github.uuidcode.builder.html.HtmlBuilder.body;
import static com.github.uuidcode.builder.html.HtmlBuilder.button;
import static com.github.uuidcode.builder.html.HtmlBuilder.div;
import static com.github.uuidcode.builder.html.HtmlBuilder.form;
import static com.github.uuidcode.builder.html.HtmlBuilder.head;
import static com.github.uuidcode.builder.html.HtmlBuilder.html;
import static com.github.uuidcode.builder.html.HtmlBuilder.img;
import static com.github.uuidcode.builder.html.HtmlBuilder.input;
import static com.github.uuidcode.builder.html.HtmlBuilder.label;
import static com.github.uuidcode.builder.html.HtmlBuilder.li;
import static com.github.uuidcode.builder.html.HtmlBuilder.link;
import static com.github.uuidcode.builder.html.HtmlBuilder.script;
import static com.github.uuidcode.builder.html.HtmlBuilder.span;
import static com.github.uuidcode.builder.html.HtmlBuilder.text;
import static com.github.uuidcode.builder.html.HtmlBuilder.ul;
import static com.github.uuidcode.builder.html.HtmlBuilder.a;

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
        this.internalSelectBox(nameList, "selectBox");

        nameList.add("3");
        this.internalSelectBox(nameList, "bigSelectBox");
    }

    private void internalSelectBox(List<String> nameList, String filename) {
        Div div = div(
            input(),
            a(
                text("heart"),
                span().addClass("ico_comm")
            ),
            this.createContentTag(nameList),
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

        this.assertHtml(div.html(), filename);
    }

    private Node createContentTag(List<String> nameList) {
        Ul ul = ul(this.createLiTagList(nameList)).addClass("list_opt");

        if (nameList.size() > 3) {
            return div(ul).addClass("box_opt");
        } else {
            return ul;
        }
    }

    private List<Node> createLiTagList(List<String> nameList) {
        return nameList.stream()
            .map(i -> a(text(i)).setId("type_" + i)
                    .setHref("http://www.google.com?q=" + i))
            .map(a -> li(a))
            .collect(Collectors.toList());
    }

    @Test
    public void imageTag() {
        this.assertHtml(img().setSrc("https://assets-cdn.github.com/images/modules/site/universe-logo.png").html(), "img");
    }

    @Test
    public void htmlTag() {
        String html = html(
            head(),
            body(
                div(text("Hello, World!"))
            )
        ).html();

        this.assertHtml(html, "html");
    }

    @Test
    public void bootstrap() {
        String html = html(
            head(
                link().stylesheet().setHref("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"),
                link().stylesheet().setHref("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"),
                script().setSrc("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js")
            ),
            body(
                div().addClass("panel-body").add(
                    form().addClass("form-horizontal").add(
                        div().addClass("form-group").add(
                            label().addClass("col-sm-2", "control-label").setFor("inputEmail").add(
                                text("Email")
                            ),
                            div(
                                input().text().addClass("form-control").setId("inputEmail").setPlaceholder("Email")
                            ).addClass("col-sm-10")
                        ),
                        div().addClass("form-group").add(
                            label().addClass("col-sm-2", "control-label").setFor("inputPassword").add(
                                text("Password")
                            ),
                            div().addClass("col-sm-10").add(
                                input().password().addClass("form-control").setId("inputPassword").setPlaceholder("Password")
                            )
                        ),
                        div().addClass("form-group").add(
                            div().addClass("col-sm-offset-2", "col-sm-10").add(
                                button().submit().addClass("btn", "btn-primary").add(
                                    text("Sing in")
                                )
                            )
                        )
                    )
                )
            )
        ).html();

        this.assertHtml(html, "bootstrap");
    }
}
