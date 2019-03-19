package com.github.uuidcode.builder.domain;

import java.util.Objects;
import java.util.function.Predicate;

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

    public boolean isSameName(String name) {
        return Objects.equals(this.name, name);
    }

    public static Predicate<Person> sameName(String name) {
        return person -> Objects.equals(person.getName(), name);
    }
}
