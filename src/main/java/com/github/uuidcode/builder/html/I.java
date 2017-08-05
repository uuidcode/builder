package com.github.uuidcode.builder.html;

public class I extends Node<I> {
    public static I of() {
        return new I().setTagName("i");
    }
}