package com.github.uuidcode.builder.html;

public class Strong extends Node<Strong> {
    public static Strong of() {
        return new Strong().setTagName("strong");
    }
}