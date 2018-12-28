package com.github.uuidcode.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.uuidcode.adapter.DateTypeAdapter;
import com.github.uuidcode.adapter.LongTypeAdapter;
import com.github.uuidcode.adapter.StringTypeAdapter;
import com.google.common.base.CaseFormat;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CoreUtil {
    protected static Logger logger = LoggerFactory.getLogger(CoreUtil.class);

    private static DateTimeParser[] dateTimeParsers = {
        DateTimeFormat.forPattern("yyyyMMdd").getParser(),
        DateTimeFormat.forPattern("yyyyMMddHH").getParser(),
        DateTimeFormat.forPattern("yyyyMMddHHmm").getParser(),
        DateTimeFormat.forPattern("yyyyMMddHHmmss").getParser(),

        DateTimeFormat.forPattern("yyyy-MM-dd").getParser(),
        DateTimeFormat.forPattern("yyyy-MM-dd HH").getParser(),
        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").getParser(),
        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").getParser(),

        DateTimeFormat.forPattern("yyyy/MM/dd").getParser(),
        DateTimeFormat.forPattern("yyyy/MM/dd HH").getParser(),
        DateTimeFormat.forPattern("yyyy/MM/dd HH:mm").getParser(),
        DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss").getParser(),

        DateTimeFormat.forPattern("MM/dd/yyyy").getParser(),
        DateTimeFormat.forPattern("MM/dd/yyyy HH").getParser(),
        DateTimeFormat.forPattern("MM/dd/yyyy HH:mm").getParser(),
        DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss").getParser(),

        DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss").withLocale(Locale.US).getParser(),

        DateTimeFormat.forPattern("yyyy.MM.dd").getParser(),
        DateTimeFormat.forPattern("yyyy.MM.dd HH").getParser(),
        DateTimeFormat.forPattern("yyyy.MM.dd HH:mm").getParser(),
        DateTimeFormat.forPattern("yyyy.MM.dd HH:mm:ss").getParser(),

        DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss").getParser(),
        DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ").getParser(),
        DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").getParser(),
        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS").getParser(),
        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSSZ").getParser()
    };

    private static DateTimeFormatter dateTimeFormatter =
        new DateTimeFormatterBuilder()
            .append(null, dateTimeParsers)
            .toFormatter();

    private static Gson gson = getGsonBuilder().create();

    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder()
            .registerTypeAdapter(Long.class, new LongTypeAdapter())
            .registerTypeAdapter(String.class, new StringTypeAdapter())
            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .addSerializationExclusionStrategy(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                    if (fieldAttributes.getDeclaredClass().equals(Class.class)) {
                        return true;
                    }

                    return false;
                }

                @Override
                public boolean shouldSkipClass(Class<?> aClass) {
                    return false;
                }
            });
    }

    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String format(Date date) {
        FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static Date parseDateTime(String text) {
        if (text == null) {
            return null;
        }

        try {
            return dateTimeFormatter.parseDateTime(text).toDate();
        } catch (Throwable t) {
        }

        try {
            long time = new Date().parse(text);
            return new Date(time);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static String wrapWithDoubleQuote(String text) {
        return wrap(text, "\"");
    }

    public static String wrapWithBrace(String text) {
        return wrap(text, leftBrace(), rightBrace());
    }

    public static String wrapWithAngleBracket(String text) {
        return wrap(text, leftAngleBracket(), rightAngleBracket());
    }

    public static String wrap(String text, String left, String right) {
        return left + text + right;
    }

    public static String wrap(String text, String left) {
        return wrap(text, left, left);
    }

    public static String toFirstCharUpperCase(String name) {
        if (isEmpty(name)) {
            return empty();
        }

        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static String toFirstCharLowerCase(String name) {
        if (isEmpty(name)) {
            return empty();
        }

        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    public static List<String> splitWithUnderscore(String text) {
        return split(text, CoreUtil.underscore());
    }

    public static List<String> split(String text, String delimiter) {
        if (text == null) {
            return new ArrayList<>();
        }

        return Arrays.stream(text.split(delimiter))
            .collect(Collectors.toList());
    }

    public static String generate(String text, int size) {
        return IntStream.range(0, size)
            .mapToObj(i -> text)
            .collect(Collectors.joining());
    }

    public static String joining(List<String> list) {
        return CoreUtil.joining(list, CoreUtil.comma());
    }

    public static String joiningWithCommaAndSpace(List<String> list) {
        return CoreUtil.joining(list, CoreUtil.commaAndSpace());
    }

    public static String joining(List<String> list, String delimiter) {
        if (list == null) {
            return CoreUtil.empty();
        }

        return list.stream().collect(Collectors.joining(delimiter));
    }

    public static <E extends Enum<E>> List<E> toList(Class<E> enumClass) {
        return new ArrayList(EnumSet.allOf(enumClass));
    }

    public static void setContent(File file, String data) {
        try {
            FileUtils.writeStringToFile(file, data, Charset.defaultCharset());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(">>> error setContent", e);
            }
        }
    }

    public static String getContent(File file) {
        try {
            return FileUtils.readFileToString(file, Charset.defaultCharset());
        } catch (Exception e) {
            logger.error("error", e);
        }

        return empty();
    }

    public static String getContentFromResource(String name) {
        String fileName = CoreUtil.class.getClassLoader().getResource(name).getFile();
        File file = new File(fileName);
        return getContent(file);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static List<Map> fromJsonToList(String json) {
        return gson.fromJson(json, new GenericOf<>(List.class, Map.class));
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> List<T> asList(T... array) {
        List<T> list = new ArrayList<>();

        for (T t : array) {
            list.add(t);
        }

        return list;
    }

    public static String lineSeparator() {
        return System.getProperty("line.separator");
    }

    public static String lineSeparator(int size) {
        return CoreUtil.generate(CoreUtil.lineSeparator(), size);
    }

    public static String indent(int size) {
        return CoreUtil.generate("    ", size);
    }

    public static String indent() {
        return indent(1);
    }

    public static String indent(String content) {
        if (CoreUtil.isEmpty(content)) {
            return CoreUtil.empty();
        }

        return indent() + content;
    }

    public static String commaAndSpace() {
        return CoreUtil.comma() + CoreUtil.space();
    }

    public static String comma() {
        return ",";
    }

    public static String space() {
        return " ";
    }

    public static String equal() {
        return "=";
    }

    public static String sharp() {
        return "#";
    }

    public static String underscore() {
        return "_";
    }

    public static String empty() {
        return "";
    }

    public static String semicolon() {
        return ";";
    }

    public static String slash() {
        return "/";
    }

    public static String backslash() {
        return "\\";
    }

    public static String dot() {
        return ".";
    }

    public static String leftBrace() {
        return "{";
    }

    public static String rightBrace() {
        return "}";
    }

    public static String leftParenthesis() {
        return "(";
    }

    public static String rightParenthesis() {
        return ")";
    }

    public static String leftAngleBracket() {
        return "<";
    }

    public static String rightAngleBracket() {
        return ">";
    }

    public static String underscoreToLowerCamel(String value) {
        if (value.contains(underscore())) {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value);
        }

        return CoreUtil.toFirstCharLowerCase(value);
    }

    public static String underscoreToUpperCamel(String value) {
        if (value.contains(underscore())) {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, value);
        }

        return CoreUtil.toFirstCharUpperCase(value);
    }

    public static String dotToSlash(String value) {
        return value.replaceAll(backslash() + dot(), slash());
    }

    public static String getCanonicalPath(File file) {
        try {
            return file.getCanonicalPath();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static String toQueryStringWithQuestionMark(Object object) {
        return toQueryStringWithQuestionMark(FieldNamingPolicy.IDENTITY, object);
    }

    public static String toQueryStringWithQuestionMark(FieldNamingPolicy fieldNamePolicy, Object object) {
        return "?" + toQueryString(fieldNamePolicy, object);
    }

    public static String toQueryString(Object object) {
        return toQueryString(FieldNamingPolicy.IDENTITY, object);
    }

    public static String toQueryString(FieldNamingPolicy fieldNamePolicy, Object object) {
        if (object == null) {
            return "";
        }

        return toNameValuePairList(fieldNamePolicy, object)
            .stream()
            .map(p -> p.getName() + "=" + CoreUtil.urlEncode(p.getValue()))
            .collect(Collectors.joining("&"));
    }

    public static List<NameValuePair> toNameValuePairList(Object object) {
        return toNameValuePairList(FieldNamingPolicy.IDENTITY, object);
    }

    public static List<NameValuePair> toNameValuePairList(FieldNamingPolicy fieldNamePolicy, Object object) {
        List<NameValuePair> list = new ArrayList<>();

        if (object == null) {
            return list;
        }

        if (object instanceof Map) {
            return toNameValuePairList(fieldNamePolicy, (Map<?, ?>) object);
        }

        return Arrays
            .stream(FieldUtils.getAllFields(object.getClass()))
            .filter(field -> !Modifier.isStatic(field.getModifiers()))
            .filter(field -> !Modifier.isFinal(field.getModifiers()))
            .map(field -> {
                field.setAccessible(true);

                Object currentObject = null;

                try {
                    currentObject = field.get(object);
                } catch (Exception e) {
                }

                if (currentObject != null) {
                    String value = getValue(currentObject);
                    String name = fieldNamePolicy.translateName(field);
                    return new BasicNameValuePair(name, value);
                }

                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private static List<NameValuePair> toNameValuePairList(FieldNamingPolicy fieldNamePolicy,
                                                           Map<?, ?> object) {
        Map<?, ?> map = object;

        return map.entrySet()
            .stream()
            .map(i -> {
                try {
                    Field field = createField(i.getKey().toString());
                    String name = fieldNamePolicy.translateName(field);
                    String value = getValue(i.getValue());
                    return new BasicNameValuePair(name, value);
                } catch (Exception e) {
                    logger.error("error", e);
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private static String getValue(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Date) {
            Date date = (Date) value;
            return CoreUtil.format(date);
        }

        return value.toString();
    }

    private static Field createField(String name) throws Exception {
        Constructor constructor = Field.class.getDeclaredConstructor(
            Class.class, String.class, Class.class, int.class, int.class, String.class, byte[].class);
        constructor.setAccessible(true);
        return (Field) constructor.newInstance(null, name, null, 0, 0, null, new byte[0]);
    }

    public static String urlEncode(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (Exception e) {
            logger.error("error", e);
        }

        return "";
    }


    public static String urlDecode(String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (Exception e) {
            logger.error("error", e);
        }

        return "";
    }
}