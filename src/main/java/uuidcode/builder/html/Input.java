package uuidcode.builder.html;

public class Input extends Node<Input> {
    public static Input of() {
        return new Input().setTagName("input")
            .setRequiresEndTag(false);
    }
}