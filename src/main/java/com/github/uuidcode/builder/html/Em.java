package com.github.uuidcode.builder.html;

public class Em extends Node<Em> {
    public static Em of() {
        return new Em().setTagName("em");
    }
}