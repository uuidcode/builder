package uuidcode.builder.html;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {
    public static String wrapWithDoubleQuotation(String text) {
        return wrap("\"", text, "\"");
    }

    public static String wrap(String left, String text, String right) {
        return left + text + right;
    }

    public static String startTag(String text) {
        return wrap("<", text, ">");
    }

    public static String endTag(String text) {
        return wrap("</", text, ">");
    }

    public static String joiningWithSpace(Set<String> set) {
        return set.stream().collect(Collectors.joining(" "));
    }

    public static String joiningWithLineSeparator(List<String> list) {
        return list.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}
