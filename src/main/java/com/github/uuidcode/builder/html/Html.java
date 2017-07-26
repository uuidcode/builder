package com.github.uuidcode.builder.html;

public class Html extends Node<Html> {
    public static Html of() {
        return new Html().setTagName("html");
    }
}
