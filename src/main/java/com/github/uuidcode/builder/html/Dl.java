package com.github.uuidcode.builder.html;

public class Dl extends Node<Dl> {
    public static Dl of() {
        return new Dl().setTagName("dl");
    }
}