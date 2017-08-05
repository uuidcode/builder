package com.github.uuidcode.builder.html;

public class Blockquote extends Node<Blockquote> {
    public static Blockquote of() {
        return new Blockquote().setTagName("blockquote");
    }
}