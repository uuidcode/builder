package com.github.uuidcode.builder.html;

public class Dd extends Node<Dd> {
    public static Dd of() {
        return new Dd().setTagName("dd");
    }
}