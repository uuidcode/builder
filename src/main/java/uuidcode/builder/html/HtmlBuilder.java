package uuidcode.builder.html;

import java.util.List;

public class HtmlBuilder {
    public static Div div(Node... nodes) {
        return Div.of().add(nodes);
    }

    public static Ul ul(Node... nodes) {
        return Ul.of().add(nodes);
    }

    public static Ul ul(List<Node> nodeList) {
        return Ul.of().add(nodeList);
    }

    public static Li li(Node... nodes) {
        return Li.of().add(nodes);
    }

    public static A a(Node... nodes) {
        return A.of().add(nodes);
    }

    public static Input input() {
        return Input.of();
    }

    public static Span span(Node... nodes) {
        return Span.of().add(nodes);
    }

    public static Text text(String text) {
        return Text.of(text);
    }

    public static Script script(Text... texts) {
        return Script.of().add(texts);
    }

    public static Img img() {
        return Img.of();
    }
}