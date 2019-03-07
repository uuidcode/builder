package com.github.uuidcode.builder.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import com.github.uuidcode.util.StringStream;

import static com.github.uuidcode.builder.pojo.PojoBuilder.getPropertyListFromMap;
import static com.github.uuidcode.util.CoreUtil.LINE_FEED;
import static com.github.uuidcode.util.CoreUtil.getCanonicalPath;
import static com.github.uuidcode.util.CoreUtil.multipleEmptyLineToOneEmptyLine;
import static com.github.uuidcode.util.CoreUtil.setContent;
import static org.slf4j.LoggerFactory.getLogger;

public class Pojo {
    private static Logger logger = getLogger(Pojo.class);
    private static List<Pojo> pojoList = new ArrayList<>();

    private String packageName;
    private String className;
    private List<Property> propertyList;

    public boolean hasProperty() {
        return this.propertyList.size() > 0;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public Pojo setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public boolean getHasDateType() {
        return this.propertyList.stream()
            .anyMatch(Property::isDate);
    }

    public boolean getHasListType() {
        return this.propertyList.stream()
            .anyMatch(Property::getIsList);
    }

    public boolean getHasPackageName() {
        return this.packageName != null;
    }

    public String getClassName() {
        return this.className;
    }

    public Pojo setClassName(String className) {
        this.className = className;
        return this;
    }

    public List<Property> getPropertyList() {
        return this.propertyList;
    }

    public Pojo setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
        return this;
    }

    public String generate() {
        return StringStream.of()
            .add("package " + this.packageName + ";", this.getHasPackageName())
            .add("", this.getHasPackageName())
            .add("import java.util.Date;", this.getHasDateType())
            .add("import java.util.List;", this.getHasListType())
            .add("")
            .add("public class " + this.className + " {" )
            .add(this.getFieldContent())
            .add("")
            .add(this.getOfMethod())
            .add(this.getMethodContent())
            .add("}")
            .joiningWithLineFeed();
    }

    private String getOfTemplate() {
        return StringStream.of()
            .add("    public static class of() {")
            .add("        return new class();")
            .add("    }")
            .joiningWithLineFeed();
    }

    private String getOfMethod() {
        return this.getOfTemplate()
            .replaceAll("class", this.className);
    }

    public String getFieldContent() {
        return this.propertyList.stream()
            .map(Property::getFieldContent)
            .collect(Collectors.joining(LINE_FEED));
    }

    public String getMethodContent() {
        return this.propertyList.stream()
            .map(Property::getMethodContent)
            .collect(Collectors.joining(LINE_FEED));
    }

    public String generateAndPrint() {
        String content = this.generate();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> generateAndSave content: \n{}", content);
        }

        return content;
    }

    public void generateAndSave(String targetDirectory) {
        String content = this.generateAndPrint();

        if (targetDirectory == null) {
            return;
        }

        this.save(targetDirectory, content);
    }

    private void save(String targetDirectory, String content) {
        content = multipleEmptyLineToOneEmptyLine(content);
        File file = new File(targetDirectory, this.className + ".java");

        if (logger.isDebugEnabled()) {
            logger.debug(">>> save file: {}", getCanonicalPath(file));
        }

        setContent(file, content);
    }

    private void processProperty() {
        if (this.propertyList == null) {
            return;
        }

        this.propertyList = this.propertyList.stream()
            .filter(Property::isAvailable)
            .map(property -> property.setClassName(this.className))
            .map(Property::processName)
            .collect(Collectors.toList());
    }

    private void processPropertyForNewType() {
        this.propertyList.stream()
            .filter(Property::getNewType)
            .forEach(this::build);
    }

    private void build(Property property) {
        Map<String, Object> valueAsMap = property.getValueAsMap();
        List<Property> propertyListFromMap = getPropertyListFromMap(valueAsMap);

        Pojo.of()
            .setPackageName(this.packageName)
            .setClassName(property.getType())
            .setPropertyList(propertyListFromMap)
            .build();
    }

    public List<Pojo> build() {
        pojoList.add(this);
        this.processProperty();
        this.processPropertyForNewType();
        return pojoList;
    }

    public static Pojo of() {
        return new Pojo();
    }
}
