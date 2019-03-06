package com.github.uuidcode.builder.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;

import static com.github.uuidcode.builder.pojo.Property.TYPE_BOOLEAN;
import static com.github.uuidcode.builder.pojo.Property.TYPE_DATE;
import static com.github.uuidcode.builder.pojo.Property.TYPE_LONG;
import static com.github.uuidcode.builder.pojo.Property.TYPE_STRING;
import static org.slf4j.LoggerFactory.getLogger;

class PropertyTypeConverter {
    private static Logger logger = getLogger(PropertyTypeConverter.class);
    private static Map<Class, Function<Property, Property>> convertMap = new HashMap<>();

    static {
        convertMap.put(String.class, PropertyTypeConverter::stringType);
        convertMap.put(Boolean.class, PropertyTypeConverter::booleanType);
        convertMap.put(Double.class, PropertyTypeConverter::doubleType);
        convertMap.put(Map.class, PropertyTypeConverter::mapType);
        convertMap.put(List.class, PropertyTypeConverter::listType);
    }

    static Property convert(Property property) {
        if (property == null) {
            return null;
        }

        Object object = property.getValue();

        if (object == null) {
            return property.setType(TYPE_STRING);
        }

        return convert(property, object);
    }

    private static Property convert(Property property, Object object) {
        Class<?> clazz = getClass(object);

        if (logger.isDebugEnabled()) {
            logger.debug(">>> convert clazz: {}", clazz.getName());
        }

        return convertMap.get(clazz).apply(property);
    }

    private static Class getClass(Object object) {
        Class<?> clazz = object.getClass();

        if (object instanceof List) {
            return List.class;
        }

        if (object instanceof Map) {
            return Map.class;
        }

        return clazz;
    }

    static Property stringType(Property property) {
        Object object = property.getValue();
        Date date = CoreUtil.parseDateTime(object.toString());

        if (date != null) {
            return property.setType(TYPE_DATE);
        }

        return property.setType(TYPE_STRING);
    }

    static Property booleanType(Property property) {
        return property.setType(TYPE_BOOLEAN);
    }

    static Property doubleType(Property property) {
        return property.setType(TYPE_LONG);
    }

    static Property mapType(Property property) {
        String name = property.getName();
        String javaType = PojoBuilder.getJavaType(name);

        return property.setType(javaType)
            .setNewType(true);
    }

    static Property listType(Property property) {
        List object = (List) property.getValue();
        String name = property.getName();

        Object itemObject = null;

        if (object.size() > 0) {
            itemObject = object.get(0);
        }

        Property itemProperty = Property.of()
            .setName(name)
            .setValue(itemObject);

        return convert(itemProperty).setIsList(true);
    }
}
