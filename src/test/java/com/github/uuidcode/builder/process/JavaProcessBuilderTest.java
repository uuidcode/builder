package com.github.uuidcode.builder.process;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.github.uuidcode.util.CoreUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaProcessBuilderTest {
    @Test
    public void getCommand() {
        String command = JavaProcessBuilder.of()
            .setClassName("Test")
            .getCommand();

        assertThat(command).isEqualTo("java Test");
    }

    @Test
    public void getCommand2() {
        String command = JavaProcessBuilder.of()
            .setClassName("Test")
            .addOption("-Xmx1G")
            .getCommand();

        assertThat(command).isEqualTo("java -Xmx1G Test");
    }

    @Test
    public void getCommand3() {
        String command = JavaProcessBuilder.of()
            .setClassName("Test")
            .addOption("-Xmx1G")
            .addArgument("dev")
            .getCommand();

        assertThat(command).isEqualTo("java -Xmx1G Test dev");
    }

    @Test
    public void getCommand4() {
        String command = JavaProcessBuilder.of()
            .setClassName("com.github.uuidcode.Test")
            .addOption("-Xmx1G")
            .addOption("-Xms256M")
            .addArgument("dev")
            .addClasspath("target/classes")
            .addClasspath("lib/junit.jar")
            .getCommand();

        List<String> classpathList = new ArrayList<>();
        classpathList.add("target/classes");
        classpathList.add("lib/junit.jar");
        String classpath = classpathList.stream()
            .collect(Collectors.joining(CoreUtil.pathSeparator()));

        assertThat(command).isEqualTo("java -Xmx1G -Xms256M -cp " + classpath + " com.github.uuidcode.Test dev");
    }
}