package com.github.uuidcode.builder.html;

public class Code extends Node<Code> {
    public static Code of() {
        return new Code().setTagName("code");
    }
}