package uuidcode.builder.html;

public class Span extends Node<Span> {
    public static Span of() {
        return new Span().setTagName("span");
    }
}