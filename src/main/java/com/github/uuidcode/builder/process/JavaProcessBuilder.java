package com.github.uuidcode.builder.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;

public class JavaProcessBuilder implements ProcessBuilder {
    public static final String MEGA = "M";
    public static final String GIGA = "G";
    protected static Logger logger = getLogger(JavaProcessBuilder.class);

    private List<String> optionList = new ArrayList<>();
    private List<String> argumentList = new ArrayList<>();
    private List<String> classpathList = new ArrayList<>();
    private String className;

    public static JavaProcessBuilder of() {
        return new JavaProcessBuilder();
    }

    public JavaProcessBuilder printGCDetails() {
        return this.addOption("-XX:+PrintGCDetails");
    }

    public JavaProcessBuilder printGCDateStamps() {
        return this.addOption("-XX:+PrintGCDateStamps");
    }

    public JavaProcessBuilder loggc(String file) {
        return this.addOption("-Xloggc:" + file);
    }

    private JavaProcessBuilder Xmn(String value) {
        return this.addOption("-Xmn" + value);
    }

    public JavaProcessBuilder XmnMega(int value) {
        return this.Xmn(value + MEGA);
    }

    public JavaProcessBuilder XmnGiga(int value) {
        return this.Xmn(value + GIGA);
    }

    private JavaProcessBuilder Xms(String value) {
        return this.addOption("-Xms" + value);
    }

    public JavaProcessBuilder XmsMega(int value) {
        return this.Xms(value + MEGA);
    }

    public JavaProcessBuilder XmsGiga(int value) {
        return this.Xms(value + GIGA);
    }

    private JavaProcessBuilder Xmx(String value) {
        return this.addOption("-Xmx" + value);
    }

    public JavaProcessBuilder XmxMega(int value) {
        return this.Xms(value + MEGA);
    }

    public JavaProcessBuilder XmxGiga(int value) {
        return this.Xms(value + GIGA);
    }

    public JavaProcessBuilder addOption(String option) {
        this.optionList.add(option);
        return this;
    }

    public JavaProcessBuilder addOption(List<String> optionList) {
        this.optionList.addAll(optionList);
        return this;
    }

    public JavaProcessBuilder addArgument(String argument) {
        this.argumentList.add(argument);
        return this;
    }

    public JavaProcessBuilder addClasspath(String classpath) {
        this.classpathList.add(classpath);
        return this;
    }

    public JavaProcessBuilder addClasspath(List<String> classpathList) {
        this.classpathList.addAll(classpathList);
        return this;
    }

    public JavaProcessBuilder addDefaultClasspath() {
        return this.addClasspath("target/classes");
    }

    public JavaProcessBuilder setClassName(String className) {
        this.className = className;
        return this;
    }

    public JavaProcessBuilder setClassName(Class clazz) {
        return this.setClassName(clazz.getName());
    }

    @Override
    public String getCommand() {
        List<String> commandList = new ArrayList<>();

        commandList.add("java");
        commandList.addAll(this.optionList);

        if (CoreUtil.isNotEmpty(this.classpathList)) {
            commandList.add("-cp");
            commandList.add(this.createClassPath());
        }

        commandList.add(this.className);
        commandList.addAll(this.argumentList);

        return commandList.stream().collect(joining(CoreUtil.SPACE));
    }

    public String createClassPath() {
        return this.classpathList.stream()
            .collect(joining(CoreUtil.pathSeparator()));
    }
}
