package com.github.uuidcode.builder.html;

import java.util.Arrays;
import java.util.List;

public class Text extends Node<Text> {
    private String text;

    public Text setText(String text) {
        this.text = text;
        return this;
    }

    public static Text of(String text) {
        return new Text().setText(text);
    }

    public List<String> contentList() {
        return Arrays.asList(this.text);
    }
}