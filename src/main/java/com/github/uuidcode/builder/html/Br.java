package com.github.uuidcode.builder.html;

public class Br extends Node<Br> {
    public static Br of() {
        return new Br().setTagName("br").setRequiresEndTag(false);
    }
}