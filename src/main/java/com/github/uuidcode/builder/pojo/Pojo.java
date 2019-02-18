package com.github.uuidcode.builder.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static com.github.uuidcode.builder.pojo.PojoBuilder.getPropertyListFromMap;
import static com.github.uuidcode.util.CoreUtil.multipleEmptyLineToOneEmptyLine;
import static com.github.uuidcode.util.CoreUtil.setContent;
import static org.slf4j.LoggerFactory.getLogger;

public class Pojo {
    protected static Logger logger = getLogger(Pojo.class);

    private String packageName;
    private String className;
    private List<Property> propertyList;
    private List<Pojo> pojoList;

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

    public List<Pojo> getPojoList() {
        return this.pojoList;
    }

    public Pojo setPojoList(List<Pojo> pojoList) {
        this.pojoList = pojoList;
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
        return CoreUtil.template("pojo", this);
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
        this.save(targetDirectory, content);
    }

    private void save(String targetDirectory, String content) {
        content = multipleEmptyLineToOneEmptyLine(content);
        File file = new File(targetDirectory, this.className + ".java");
        setContent(file, content);
    }

    private List<Pojo> build(List<Pojo> pojoList) {
        this.processProperty();
        this.setPropertyList(this.propertyList);
        pojoList.add(this);
        this.processPropertyForNewType(pojoList);

        return pojoList;
    }

    private void processProperty() {
        this.propertyList = this.propertyList
            .stream()
            .filter(Property::isAvailable)
            .map(Property::processName)
            .collect(Collectors.toList());
    }

    private void processPropertyForNewType(List<Pojo> pojoList) {
        this.propertyList.stream()
            .filter(Property::getNewType)
            .forEach(property -> build(pojoList, property));
    }

    private void build(List<Pojo> pojoList, Property property) {
        Map<String, Object> valueAsMap = property.getValueAsMap();
        List<Property> propertyListFromMap = getPropertyListFromMap(valueAsMap);

        Pojo.of()
            .setPackageName(this.packageName)
            .setPojoList(pojoList)
            .setClassName(property.getType())
            .setPropertyList(propertyListFromMap)
            .build(pojoList);
    }

    public List<Pojo> build() {
        List<Pojo> pojoList = new ArrayList<>();
        this.build(pojoList);
        return pojoList;
    }

    public static Pojo of() {
        return new Pojo();
    }
}
