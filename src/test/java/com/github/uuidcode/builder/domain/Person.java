package com.github.uuidcode.builder.domain;

public class Person {
    private String name;

    public static Person of() {
        return new Person();
    }

    public String getName() {
        return this.name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }
}
