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

public class PropertyConverter {
    protected static Logger logger = getLogger(PropertyConverter.class);
    private static Map<Class, Function<Property, Property>> map = new HashMap<>();

    static {
        map.put(String.class, StringTypeConverter::convert);
        map.put(Boolean.class, BooleanTypeConverter::convert);
        map.put(Double.class, DoubleTypeConverter::convert);
        map.put(Map.class, MapTypeConverter::convert);
        map.put(List.class, ListTypeConverter::convert);
    }

    public static Property convert(Property property) {
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
        try {
            Class<?> clazz = getClass(object);

            if (logger.isDebugEnabled()) {
                logger.debug(">>> convert clazz: {}", clazz.getName());
            }

            return map.get(clazz).apply(property);
        } catch (Throwable t) {
            if (logger.isErrorEnabled()) {
                logger.error(">>> error TypeConverter convert", t);
            }

            throw t;
        }
    }

    public static Class getClass(Object object) {
        Class<?> clazz = object.getClass();

        if (object instanceof List) {
            return List.class;
        }

        if (object instanceof Map) {
            return Map.class;
        }

        return clazz;
    }

    public static class StringTypeConverter {
        public static Property convert(Property property) {
            Object object = property.getValue();
            Date date = CoreUtil.parseDateTime(object.toString());

            if (date != null) {
                return property.setType(TYPE_DATE);
            }

            return property.setType(TYPE_STRING);
        }
    }

    public static class BooleanTypeConverter {
        public static Property convert(Property property) {
            return property.setType(TYPE_BOOLEAN);
        }
    }

    public static class DoubleTypeConverter {
        public static Property convert(Property property) {
            return property.setType(TYPE_LONG);
        }
    }

    public static class MapTypeConverter {
        public static Property convert(Property property) {
            String name = property.getName();
            String javaType = PojoBuilder.getJavaType(name);

            return property.setType(javaType)
                .setNewType(true);
        }
    }

    public static class ListTypeConverter {
        public static Property convert(Property property) {
            return processListType(property);
        }
    }

    public static Property processListType(Property property) {
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
