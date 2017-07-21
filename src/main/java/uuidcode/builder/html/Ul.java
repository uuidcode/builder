package uuidcode.builder.html;

public class Ul extends Node<Ul> {
    public static Ul of() {
        return new Ul().setTagName("ul");
    }
}