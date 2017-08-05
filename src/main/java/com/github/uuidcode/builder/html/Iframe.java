package com.github.uuidcode.builder.html;

public class Iframe extends Node<Iframe> {
    public static Iframe of() {
        return new Iframe().setTagName("iframe");
    }
}