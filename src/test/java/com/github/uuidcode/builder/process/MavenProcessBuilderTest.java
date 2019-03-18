package com.github.uuidcode.builder.process;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class MavenProcessBuilderTest {
    protected static Logger logger = getLogger(MavenProcessBuilderTest.class);

    @Test
    public void getLibraryList() {
        List<String> libraryList = MavenProcessBuilder.of().getLibraryList();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> getLibraryList libraryList: {}", CoreUtil.toJson(libraryList));
        }
    }
    
    @Test
    public void getLibraryPathList() {
        List<String> list = MavenProcessBuilder.of().getLibraryPathList();
        
        if (logger.isDebugEnabled()) {
            logger.debug(">>> getLibraryPathList list: {}", CoreUtil.toJson(list));
        }
    }
}