package com.github.uuidcode.builder.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;

public class JavaProcessBuilder {
    protected static Logger logger = getLogger(JavaProcessBuilder.class);

    private List<String> optionList = new ArrayList<>();
    private List<String> argumentList = new ArrayList<>();
    private List<String> classpathList = new ArrayList<>();
    private String className;

    public static JavaProcessBuilder of() {
        return new JavaProcessBuilder();
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

    public JavaProcessBuilder setClassName(String className) {
        this.className = className;
        return this;
    }

    public JavaProcessBuilder setClassName(Class clazz) {
        return this.setClassName(clazz.getName());
    }

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

    public Process build() {
        try {
            String command = this.getCommand();

            if (logger.isDebugEnabled()) {
                logger.debug(">>> build command: {}", command);
            }

            return Runtime.getRuntime().exec(command);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
