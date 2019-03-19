package com.github.uuidcode.util;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;

import static com.github.uuidcode.util.OptionalEx.log;
import static org.slf4j.LoggerFactory.getLogger;

public class OptionalExTest {
    protected static Logger logger = getLogger(OptionalExTest.class);

    @Test
    public void unchecked() {
        File file = new File(".");
        Optional<File> firstFileOptional = Arrays.stream(file.listFiles()).findFirst();

        OptionalEx.ofNullable(firstFileOptional)
            .map(f -> log(f, logger, ">>> getCanonicalPath {}", f.getName()))
            .mapUnchecked(File::getCanonicalPath)
            .map(name -> log(name, logger, ">>> getCanonicalPath {}", name))
            .map(String::toUpperCase)
            .map(name -> log(name, logger, ">>> toUpperCase {}", name))
            .orElse(null);
    }
}