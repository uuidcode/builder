package com.github.uuidcode.builder.jquery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JQuery {
    private List<String> list = new ArrayList<>();

    public JQuery(String selector) {
        list.add("$('" + selector + "')");
    }
    public JQuery html(String html) {
        list.add(".html('" + html + "')");
        return this;
    }

    public JQuery html() {
        list.add(".html()");
        return this;
    }

    public String generate() {
        this.list.add(";");
        return this.list.stream().collect(Collectors.joining(""));
    }
}
