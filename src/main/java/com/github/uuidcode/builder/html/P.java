package com.github.uuidcode.builder.html;

public class P extends Node<P> {
    public static P of() {
        return new P().setTagName("p");
    }
}