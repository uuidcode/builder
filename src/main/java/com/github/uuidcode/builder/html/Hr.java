package com.github.uuidcode.builder.html;

public class Hr extends Node<Hr> {
    public static Hr of() {
        return new Hr().setTagName("hr").setRequiresEndTag(false);
    }
}