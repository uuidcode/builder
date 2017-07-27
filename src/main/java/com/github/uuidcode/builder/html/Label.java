package com.github.uuidcode.builder.html;

public class Label extends Node<Label> {
    public static Label of() {
        return new Label().setTagName("label");
    }
}