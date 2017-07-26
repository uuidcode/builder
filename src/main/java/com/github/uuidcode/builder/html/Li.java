package com.github.uuidcode.builder.html;

public class Li extends Node<Li> {
    public static Li of() {
        return new Li().setTagName("li");
    }
}
