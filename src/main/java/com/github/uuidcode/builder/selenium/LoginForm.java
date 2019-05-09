package com.github.uuidcode.builder.selenium;

public class LoginForm {
    private String token;
    private String first;
    private String second;
    private String third;
    private String uri;
    private String firstSuffix;

    public String getFirstSuffix() {
        return this.firstSuffix;
    }

    public LoginForm setFirstSuffix(String firstSuffix) {
        this.firstSuffix = firstSuffix;
        return this;
    }

    public static LoginForm of() {
        return new LoginForm();
    }

    public String getUri() {
        return this.uri;
    }

    public LoginForm setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getThird() {
        return this.third;
    }

    public LoginForm setThird(String third) {
        this.third = third;
        return this;
    }

    public String getSecond() {
        return this.second;
    }

    public LoginForm setSecond(String second) {
        this.second = second;
        return this;
    }

    public String getFirst() {
        return this.first;
    }

    public LoginForm setFirst(String first) {
        this.first = first;
        return this;
    }

    public String getToken() {
        return this.token;
    }

    public LoginForm setToken(String token) {
        this.token = token;
        return this;
    }
}
