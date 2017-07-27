package com.github.uuidcode.builder.html;

public class Img extends Node<Img> {
    public static Img of() {
        return new Img().setTagName("img")
            .setRequiresEndTag(false);
    }
}
