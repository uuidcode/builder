package com.github.uuidcode.builder.html;

public class Textarea extends Node<Textarea> {
    public static Textarea of() {
        return new Textarea().setTagName("textarea");
    }
}