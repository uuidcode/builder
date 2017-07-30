package com.github.uuidcode.builder.domain;

public class Project {
    private Long projectId;
    private String name;
    private Long price;

    public static Project of() {
        return new Project();
    }

    public Long getPrice() {
        return this.price;
    }

    public Project setPrice(Long price) {
        this.price = price;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Project setName(String name) {
        this.name = name;
        return this;
    }
    public Long getProjectId() {
        return this.projectId;
    }

    public Project setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }
}
