package com.github.uuidcode.builder.process;

import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import static com.github.uuidcode.util.CoreUtil.splitListWithNewLine;
import static java.nio.charset.StandardCharsets.UTF_8;
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

    default String runAndGetResult() {
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(this.run().getInputStream(), writer, UTF_8);
            return writer.toString();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    default List<String> runAndGetResultList() {
        return splitListWithNewLine(this.runAndGetResult());
    }
}
