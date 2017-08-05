package com.github.uuidcode.builder.html;

public class Del extends Node<Del> {
    public static Del of() {
        return new Del().setTagName("del");
    }
}