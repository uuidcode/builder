package com.github.uuidcode.builder.request;

public class Data {
    private Long id;
    private String firstName;
    private String lastName;

    public String getLastName() {
        return this.lastName;
    }

    public Data setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
    public String getFirstName() {
        return this.firstName;
    }

    public Data setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
    public Long getId() {
        return this.id;
    }

    public Data setId(Long id) {
        this.id = id;
        return this;
    }

}
