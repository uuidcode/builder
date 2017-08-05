package com.github.uuidcode.builder.html;

public class Select extends Node<Select> {
    public static Select of() {
        return new Select().setTagName("select");
    }
}