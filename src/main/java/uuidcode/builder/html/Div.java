package uuidcode.builder.html;

public class Div extends Node<Div> {
    public static Div of() {
        return new Div().setTagName("div");
    }
}
