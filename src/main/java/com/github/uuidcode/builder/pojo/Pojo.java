package com.github.uuidcode.builder.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static com.github.uuidcode.util.CoreUtil.underscoreToLowerCamel;
import static org.slf4j.LoggerFactory.getLogger;

public class Pojo {
    protected static Logger logger = getLogger(Pojo.class);

    private String className;
    private Map<String, Object> map;
    private List<Property> propertyList;
    private List<Pojo> pojoList;

    public List<Pojo> getPojoList() {
        return this.pojoList;
    }

    public Pojo setPojoList(List<Pojo> pojoList) {
        this.pojoList = pojoList;
        return this;
    }

    public Map<String, Object> getMap() {
        return this.map;
    }

    public Pojo setMap(Map<String, Object> map) {
        this.map = map;
        return this;
    }

    public Pojo setJson(String json) {
        return this.setMap(CoreUtil.fromJsonToMap(json));
    }

    public boolean getHasDateType() {
        return this.propertyList.stream()
            .anyMatch(Property::isDate);
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

    public void generateAndSave(String targetDirectory) {
        String content = this.generate();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> generateAndSave content: \n{}", content);
        }

        CoreUtil.setContent(new File(targetDirectory, this.className + ".java"), content);
    }

    private List<Pojo> build(List<Pojo> pojoList) {
        boolean propertyType = this.propertyList != null;

        if (propertyType) {
            this.propertyList = this.propertyList
                .stream()
                .filter(property -> PojoBuilder.isAvailableField(property.getName()))
                .map(property -> {
                    String name = property.getName();
                    name = name.toLowerCase();
                    return property.setName(underscoreToLowerCamel(name));
                })
                .collect(Collectors.toList());
        } else {
            this.propertyList = this.map.entrySet()
                .stream()
                .filter(entry -> PojoBuilder.isAvailableField(entry.getKey()))
                .map(Property::of)
                .collect(Collectors.toList());
        }

        pojoList.add(Pojo.of()
            .setPropertyList(this.propertyList)
            .setClassName(this.className));

        if (propertyType) {
            return pojoList;
        }

        this.propertyList.stream()
            .filter(Property::getNewType)
            .forEach(property -> {
                String type = property.getType();

                Pojo.of()
                    .setPojoList(pojoList)
                    .setClassName(type)
                    .setMap((Map<String, Object>) property.getValue())
                    .build(pojoList);
            });

        return pojoList;
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
