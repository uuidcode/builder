package com.github.uuidcode.builder.html;

public class Body extends Node<Body> {
    public static Body of() {
        return new Body().setTagName("body");
    }
}
