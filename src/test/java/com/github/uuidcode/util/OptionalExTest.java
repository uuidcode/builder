package com.github.uuidcode.util;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;

import static com.github.uuidcode.util.OptionalEx.log;
import static com.github.uuidcode.util.OptionalEx.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

public class OptionalExTest {
    protected static Logger logger = getLogger(OptionalExTest.class);

    @Test
    public void unchecked() {
        File file = new File(".");

        Optional<File> firstFileOptional = ofNullable(file.listFiles())
            .map(Arrays::asList)
            .orElseList()
            .stream()
            .findFirst();

        String firstFileName = ofNullable(firstFileOptional)
            .map(f -> log(f, logger, ">>> getCanonicalPath {} {}", f.getName(), f.length()))
            .mapUnchecked(File::getCanonicalPath)
            .map(name -> log(name, logger, ">>> getCanonicalPath {}"))
            .map(String::toUpperCase)
            .map(name -> log(name, logger, ">>> toUpperCase {}"))
            .orElseNull();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> unchecked firstFileName: {}", CoreUtil.toJson(firstFileName));
        }
    }
}