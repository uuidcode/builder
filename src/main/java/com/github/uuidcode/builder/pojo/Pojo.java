package com.github.uuidcode.builder.pojo;

import java.util.List;
import java.util.stream.Collectors;

import com.github.uuidcode.util.CoreUtil;
import com.github.uuidcode.util.StringStream;

import static com.github.uuidcode.util.CoreUtil.templateInline;
import static com.github.uuidcode.util.StringStream.LINE_FEED;

public class Pojo {
    private List<Property> propertyList;

    public static Pojo of() {
        return new Pojo();
    }

    public List<Property> getPropertyList() {
        return this.propertyList;
    }

    public Pojo setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
        return this;
    }

    public String generate(String className) {
        String fieldContent = this.propertyList.stream()
            .map(Property::createField)
            .collect(Collectors.joining(LINE_FEED));

        String methodContent = this.propertyList.stream()
            .map(property -> property.createSetGetMethod(className))
            .collect(Collectors.joining(LINE_FEED));

        return StringStream.of()
            .add(fieldContent)
            .add(methodContent)
            .joiningWithLineFeed();
    }
}
