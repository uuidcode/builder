package com.github.uuidcode.builder.html;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;

public class CoreTest {
    public void assertHtml(String content, String name) {
        assertFile("html", content, name, ".html");
    }

    public void assertJs(String content, String name) {
        assertFile("js", content, name, ".js");
    }

    private void assertFile(String prefix, String html, String name, String ext) {
        File file = new File(CoreTest.class.getClassLoader().getResource(prefix + "/" + name + ext).getFile());
        List<String> lines = null;

        try {
            lines = Files.readAllLines(file.toPath());
        } catch (Throwable t) {
            Assertions.fail(t.getMessage());
        }

        assertThat(html).isEqualTo(lines.stream().collect(Collectors.joining(System.lineSeparator())));
    }
}
