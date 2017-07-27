package com.github.uuidcode.builder.html;

public class Link extends Node<Link> {
    public static Link of() {
        return new Link().setTagName("link");
    }
}
