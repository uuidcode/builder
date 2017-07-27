package com.github.uuidcode.builder.html;

public class Form extends Node<Form> {
    public static Form of() {
        return new Form().setTagName("form");
    }
}