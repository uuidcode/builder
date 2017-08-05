package com.github.uuidcode.builder.html;

public class Dt extends Node<Dt> {
    public static Dt of() {
        return new Dt().setTagName("dt");
    }
}