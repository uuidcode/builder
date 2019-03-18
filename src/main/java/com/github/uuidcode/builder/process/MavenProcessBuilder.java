package com.github.uuidcode.builder.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.uuidcode.util.CoreUtil;

import static com.github.uuidcode.util.CoreUtil.SPACE;
import static java.util.stream.Collectors.joining;

public class MavenProcessBuilder implements ProcessBuilder {
    private List<String> optionList = new ArrayList<>();

    public static MavenProcessBuilder of() {
        return new MavenProcessBuilder();
    }

    MavenProcessBuilder() {
        this.optionList.add("mvn");
    }

    public MavenProcessBuilder dependencyList() {
        this.optionList.add("dependency:list");
        return this;
    }

    public MavenProcessBuilder dependencyBuildClasspath() {
        this.optionList.add("dependency:build-classpath");
        return this;
    }

    @Override
    public String getCommand() {
        return this.optionList.stream().collect(joining(SPACE));
    }

    public boolean isLibraryFormat(List<String> list) {
        return list.size() == 5;
    }

    public boolean isCompileOrRuntime(List<String> list) {
        String scrop = list.get(4);
        return "compile".equals(scrop) || "runtime".equals(scrop);
    }

    public boolean isJar(List<String> list) {
        String type = list.get(2);
        return "jar".equals(type);
    }

    public String toFileName(List<String> list) {
        String artifactId = list.get(1);
        String version = list.get(3);

        List<String> fileItemList = new ArrayList<>();
        fileItemList.add(artifactId);
        fileItemList.add(version);

        return fileItemList.stream().collect(joining(CoreUtil.HYPHEN)) + ".jar";
    }

    public List<String> getLibraryList() {
        String result = this.dependencyList().runAndGetResult();

        return CoreUtil.splitListWithNewLine(result)
            .stream()
            .map(CoreUtil::splitListWithColon)
            .filter(this::isLibraryFormat)
            .filter(this::isCompileOrRuntime)
            .filter(this::isJar)
            .map(this::toFileName)
            .collect(Collectors.toList());
    }


    public List<String> getLibraryPathList() {
        String result = this.dependencyBuildClasspath().runAndGetResult();
        List<String> list = CoreUtil.splitListWithNewLine(result);
        int index = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("Dependencies classpath")) {
                index = i;
            }
        }

        if (index < list.size()) {
            String classPath = list.get(index + 1);
            return CoreUtil.splitList(classPath, File.pathSeparator);
        }

        return new ArrayList<>();
    }
}
