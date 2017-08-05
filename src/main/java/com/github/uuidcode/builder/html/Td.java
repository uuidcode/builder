package com.github.uuidcode.builder.html;

public class Td extends Node<Td> {
    public static Td of() {
        return new Td().setTagName("td");
    }
}
