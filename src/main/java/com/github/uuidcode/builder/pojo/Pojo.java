package com.github.uuidcode.builder.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class Pojo {
    protected static Logger logger = getLogger(Pojo.class);

    private String className;
    private List<Property> propertyList;

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

    private static List<Pojo> of(List<Pojo> pojoList,
                                 String className,
                                 Map<String, Object> map) {
        if (logger.isDebugEnabled()) {
            logger.debug(">>> of className: {}", CoreUtil.toJson(className));
        }

        List<Property> propertyList = map.entrySet()
            .stream()
            .map(Property::of)
            .collect(Collectors.toList());

        pojoList.add(Pojo.of(propertyList).setClassName(className));

        propertyList.stream()
            .filter(Property::getNewType)
            .forEach(property -> {
                String propertyType = property.getPropertyType();
                Pojo.of(pojoList, propertyType, (Map<String, Object>) property.getValue());
            });

        return pojoList;
    }

    private static List<Pojo> of(String className,
                                 Map<String, Object> map) {
        List<Pojo> pojoList = new ArrayList<>();
        return of(pojoList, className, map);
    }

    public static List<Pojo> of(String className, String json) {
        return of(className, CoreUtil.fromJsonToMap(json));
    }

    public static Pojo of() {
        return new Pojo();
    }

    public static Pojo of(List<Property> propertyList) {
        return Pojo.of().setPropertyList(propertyList);
    }
}
