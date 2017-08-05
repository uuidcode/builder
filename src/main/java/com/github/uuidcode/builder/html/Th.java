package com.github.uuidcode.builder.html;

public class Th extends Node<Th> {
    public static Th of() {
        return new Th().setTagName("th");
    }
}
