package com.github.uuidcode.builder.html;

public class Table extends Node<Table> {
    public static Table of() {
        return new Table().setTagName("table");
    }
}
