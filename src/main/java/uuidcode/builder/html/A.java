package uuidcode.builder.html;

public class A extends Node<A> {
    public static A of() {
        return new A().setTagName("a");
    }
}