package com.github.uuidcode.builder.process;

import java.util.ArrayList;
import java.util.List;

import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

public interface ProcessBuilder {
    Logger logger = getLogger(ProcessBuilder.class);

    String getCommand();

    default Process run() {
        String command = this.getCommand();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> run command: {}", command);
        }

        return ofNullable(Runtime.getRuntime())
            .map(Unchecked.function(runtime -> runtime.exec(command)))
            .orElse(null);
    }

    default String runAndGetResult() {
        return ofNullable(this.run())
            .map(Process::getInputStream)
            .map(Unchecked.function(CoreUtil::toString))
            .orElse(null);
    }

    default List<String> runAndGetResultList() {
        return ofNullable(this.runAndGetResult())
            .map(CoreUtil::splitListWithNewLine)
            .orElse(new ArrayList<>());
    }
}
