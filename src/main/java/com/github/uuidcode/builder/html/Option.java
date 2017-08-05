package com.github.uuidcode.builder.html;

public class Option extends Node<Option> {
    public static Option of() {
        return new Option().setTagName("option");
    }
}