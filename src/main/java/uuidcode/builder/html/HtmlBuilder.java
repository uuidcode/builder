package uuidcode.builder.html;

public class HtmlBuilder {
    public static Div div(Node... nodes) {
        return Div.of().add(nodes);
    }

    public static Ul ul(Node... nodes) {
        return Ul.of().add(nodes);
    }

    public static Li li(Node... nodes) {
        return Li.of().add(nodes);
    }

    public static A a(Node... nodes) {
        return A.of().add(nodes);
    }
}