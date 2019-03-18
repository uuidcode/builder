package com.github.uuidcode.builder.process;

import java.util.ArrayList;
import java.util.List;

import com.github.uuidcode.util.CoreUtil;

import static com.github.uuidcode.util.CoreUtil.SPACE;
import static com.github.uuidcode.util.CoreUtil.splitListWithFilePathSeparator;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class MavenProcessBuilder implements ProcessBuilder {
    private List<String> optionList = new ArrayList<>();

    public static MavenProcessBuilder of() {
        return new MavenProcessBuilder();
    }

    MavenProcessBuilder() {
        this.optionList.add("mvn");
    }

    public MavenProcessBuilder dependencyBuildClasspath() {
        this.optionList.add("dependency:build-classpath");
        return this;
    }

    @Override
    public String getCommand() {
        return this.optionList.stream().collect(joining(SPACE));
    }

    public List<String> getLibraryList() {
        return this.getLibraryPathList()
            .stream()
            .map(CoreUtil::splitListWithFileSeparator)
            .map(CoreUtil::last)
            .collect(toList());
    }

    public List<String> getLibraryPathList() {
        String result = this.dependencyBuildClasspath().runAndGetResult();
        List<String> list = CoreUtil.splitListWithNewLine(result);
        int index = getDependenciesClasspathIndex(list);
        String path = list.get(index + 1);

        return splitListWithFilePathSeparator(path);
    }

    private int getDependenciesClasspathIndex(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("Dependencies classpath")) {
                return i;
            }
        }

        return -1;
    }
}
