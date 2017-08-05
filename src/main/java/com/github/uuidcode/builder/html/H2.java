package com.github.uuidcode.builder.html;

public class H2 extends Node<H2> {
    public static H2 of() {
        return new H2().setTagName("h2");
    }
}