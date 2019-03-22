package com.github.uuidcode.builder.domain;

import java.util.Objects;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

public class Person {
    private String name;
    private Integer personId;

    public Integer getPersonId() {
        return this.personId;
    }

    public Person setPersonId(Integer personId) {
        this.personId = personId;
        return this;
    }

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

    public Person assertName(String name) {
        assertThat(this.name).isEqualTo(name);
        return this;
    }
}
