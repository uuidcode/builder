package com.github.uuidcode.builder.html;

public class Tbody extends Node<Tbody> {
    public static Tbody of() {
        return new Tbody().setTagName("tbody");
    }
}
