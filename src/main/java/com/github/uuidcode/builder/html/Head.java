package com.github.uuidcode.builder.html;

public class Head extends Node<Head> {
    public static Head of() {
        return new Head().setTagName("head");
    }
}
