package com.github.uuidcode.builder.process;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public interface ProcessBuilder {
    Logger logger = getLogger(ProcessBuilder.class);

    String getCommand();

    default Process run() {
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

    default  String runAndGetResult() {
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(this.run().getInputStream(), writer, StandardCharsets.UTF_8);
            return writer.toString();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
