package com.github.uuidcode.builder.html;

public class Ol extends Node<Ol> {
    public static Ol of() {
        return new Ol().setTagName("ol");
    }
}