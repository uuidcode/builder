package com.github.uuidcode.builder.html;

public class Script extends Node<Script> {
    public static Script of(Text... texts) {
        return new Script().setTagName("script").add(texts);
    }
}