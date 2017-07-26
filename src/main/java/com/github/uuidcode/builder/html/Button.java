package com.github.uuidcode.builder.html;

public class Button extends Node<Button> {
    public static Button of() {
        return new Button().setTagName("button");
    }
}
