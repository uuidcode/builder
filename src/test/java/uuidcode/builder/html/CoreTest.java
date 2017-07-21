package uuidcode.builder.html;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;

public class CoreTest {
    public void assertHtml(String html, String name) {
        File file = new File(CoreTest.class.getClassLoader().getResource(name + ".html").getFile());

        List<String> lines = null;

        try {
            lines = Files.readAllLines(file.toPath());
        } catch (Throwable t) {
            Assertions.fail(t.getMessage());
        }

        assertThat(html).isEqualTo(lines.stream().collect(Collectors.joining(System.lineSeparator())));
    }
}
