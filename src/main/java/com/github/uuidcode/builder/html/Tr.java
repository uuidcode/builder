package com.github.uuidcode.builder.html;

public class Tr extends Node<Tr> {
    public static Tr of() {
        return new Tr().setTagName("tr");
    }
}
