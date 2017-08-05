package com.github.uuidcode.builder.html;

public class H1 extends Node<H1> {
    public static H1 of() {
        return new H1().setTagName("h1");
    }
}