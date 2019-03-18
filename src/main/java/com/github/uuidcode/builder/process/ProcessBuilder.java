package com.github.uuidcode.builder.process;

import java.util.List;

import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static com.github.uuidcode.util.OptionalEx.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

public interface ProcessBuilder {
    Logger logger = getLogger(ProcessBuilder.class);

    String getCommand();

    default Process run() {
        return ofNullable(this.getCommand())
            .map(this::logCommand)
            .uncheckedMap(Runtime.getRuntime()::exec)
            .orElseNull();
    }

    default String runAndGetResult() {
        return ofNullable(this.run())
            .map(Process::getInputStream)
            .uncheckedMap(CoreUtil::toString)
            .orElseNull();
    }

    default List<String> runAndGetResultList() {
        return ofNullable(this.runAndGetResult())
            .map(CoreUtil::splitListWithNewLine)
            .orElseList();
    }

    default String logCommand(String command) {
        if (logger.isDebugEnabled()) {
            logger.debug(">>> run command: {}", command);
        }

        return command;
    }
}
