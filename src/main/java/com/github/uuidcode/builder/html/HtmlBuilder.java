package com.github.uuidcode.builder.html;

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

    public static Html html(Node... nodes) {
        return Html.of().add(nodes);
    }

    public static Head head(Node... nodes) {
        return Head.of().add(nodes);
    }

    public static Body body(Node... nodes) {
        return Body.of().add(nodes);
    }

    public static Link link(Node... nodes) {
        return Link.of().add(nodes);
    }

    public static Form form(Node... nodes) {
        return Form.of().add(nodes);
    }

    public static Label label(Node... nodes) {
        return Label.of().add(nodes);
    }

    public static Button button(Node... nodes) {
        return Button.of().add(nodes);
    }

    public static Table table(Node... nodes) {
        return Table.of().add(nodes);
    }

    public static Thead thead(Node... nodes) {
        return Thead.of().add(nodes);
    }

    public static Tbody tbody(Node... nodes) {
        return Tbody.of().add(nodes);
    }

    public static Th th(Node... nodes) {
        return Th.of().add(nodes);
    }

    public static Tr tr(Node... nodes) {
        return Tr.of().add(nodes);
    }

    public static Td td(Node... nodes) {
        return Td.of().add(nodes);
    }
}