package com.github.uuidcode.builder.string;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringBuilder {
    public static final String COMMA = ",";
    public static final String SPACE = " ";
    public static final String UNDERSCORE = "_";
    public static final String HYPHEN = "-";
    public static final String VERTICAL_BAR = "|";
    public static final String SLASH = "/";
    public static final String QUESTION_MARK = "?";
    public static final String DOT = ".";
    public static final String AMPERSAND = "&";
    public static final String NEW_LINE = System.lineSeparator();
    public static final String EQUAL = "=";

    private Stream.Builder<String> builder = Stream.builder();
    private List<IntermediateOperation> intermediateOperationList = new ArrayList<>();

    public static StringBuilder of() {
        return new StringBuilder();
    }

    public static StringBuilder of(Object object) {
        return new StringBuilder().add(object);
    }

    public static StringBuilder of(List<String> list) {
        return of(list, Function.identity());
    }

    public static <T> StringBuilder of(List<T> list, Function<T, String> mapper) {
        StringBuilder stringBuilder = StringBuilder.of();
        list.stream().map(mapper).forEach(stringBuilder::add);
        return stringBuilder;
    }

    public StringBuilder add(Object object) {
        return add(true, object);
    }

    public StringBuilder add(boolean test, Object object)  {
        if (test) {
            if (object != null) {
                this.builder.add(object.toString());
            }
        }

        return this;
    }

    public <T> StringBuilder add(boolean test, Supplier<String> supplier)  {
        return this.add(test ? supplier.get() : null);
    }

    public <T> StringBuilder add(Supplier<String> supplier)  {
        return this.add(true, supplier);
    }

    public StringBuilder map(Function<String, String> mapper) {
        this.intermediateOperationList.add(IntermediateOperation.map(mapper));
        return this;
    }

    public StringBuilder filter(Predicate<String> predicate) {
        this.intermediateOperationList.add(IntermediateOperation.filter(predicate));
        return this;
    }

    public StringBuilder skip(Long index) {
        this.intermediateOperationList.add(IntermediateOperation.skip(index));
        return this;
    }

    public String joining(CharSequence delimiter) {
        Stream<String> stream = this.builder.build();

        if (this.intermediateOperationList != null) {
            for (IntermediateOperation intermediateOperation : intermediateOperationList) {
                stream = intermediateOperation.run(stream);
            }
        }

        return stream.collect(Collectors.joining(delimiter));
    }

    public String joining() {
        return this.joining("");
    }

    public String joiningWithComma() {
        return this.joining(COMMA);
    }

    public String joiningWithCommaAndSpace() {
        return this.joining(COMMA + SPACE);
    }

    public String joiningWithCommaAndNewLine() {
        return this.joining(COMMA + NEW_LINE);
    }

    public String joiningWithSpace() {
        return this.joining(SPACE);
    }

    public String joiningWithUnderscore() {
        return this.joining(UNDERSCORE);
    }

    public String joiningWithHyphen() {
        return this.joining(HYPHEN);
    }

    public String joiningWithVerticalBar() {
        return this.joining(VERTICAL_BAR);
    }

    public String joiningWithSlash() {
        return this.joining(SLASH);
    }

    public String joiningWithQuestionMark() {
        return this.joining(QUESTION_MARK);
    }

    public String joiningWithDot() {
        return this.joining(DOT);
    }

    public String joiningWithAmpersand() {
        return this.joining(AMPERSAND);
    }

    public String joiningWithNewLine() {
        return this.joining(NEW_LINE);
    }

    public String joiningWithEqual() {
        return this.joining(EQUAL);
    }

    public StringBuilder joiningAndThen() {
        return StringBuilder.of(this.joining(""));
    }

    public StringBuilder joiningCommaAndThen() {
        return StringBuilder.of(this.joining(COMMA));
    }

    public StringBuilder joiningCommaAndSpaceAndThen() {
        return StringBuilder.of(this.joining(COMMA + SPACE));
    }

    public StringBuilder joiningSpaceAndThen() {
        return StringBuilder.of(this.joining(SPACE));
    }

    public StringBuilder joiningUnderscoreAndThen() {
        return StringBuilder.of(this.joining(UNDERSCORE));
    }

    public StringBuilder joiningHyphenAndThen() {
        return StringBuilder.of(this.joining(HYPHEN));
    }

    public StringBuilder joiningVerticalBarAndThen() {
        return StringBuilder.of(this.joining(VERTICAL_BAR));
    }

    public StringBuilder joiningSlashAndThen() {
        return StringBuilder.of(this.joining(SLASH));
    }

    public StringBuilder joiningQuestionMarkAndThen() {
        return StringBuilder.of(this.joining(QUESTION_MARK));
    }

    public StringBuilder joiningDotAndThen() {
        return StringBuilder.of(this.joining(DOT));
    }

    public StringBuilder joiningAmpersandAndThen() {
        return StringBuilder.of(this.joining(AMPERSAND));
    }

    public StringBuilder joiningNewLineAndThen() {
        return StringBuilder.of(this.joining(NEW_LINE));
    }

    public StringBuilder joiningEqualAndThen() {
        return StringBuilder.of(this.joining(EQUAL));
    }

    public StringBuilder addSpace() {
        return this.add(SPACE);
    }

    public StringBuilder addNewLine() {
        return this.add(NEW_LINE);
    }

    public StringBuilder addSlash() {
        return this.add(SLASH);
    }

    public StringBuilder addComma() {
        return this.add(COMMA);
    }

    public StringBuilder addCommaAndSpace() {
        return this.add(COMMA + SPACE);
    }

    public StringBuilder addAmpersand() {
        return this.add(AMPERSAND);
    }

    public StringBuilder addDot() {
        return this.add(DOT);
    }

    public StringBuilder addQuestionMark() {
        return this.add(QUESTION_MARK);
    }

    public StringBuilder addVerticalBar() {
        return this.add(VERTICAL_BAR);
    }

    public StringBuilder addHyphen() {
        return this.add(HYPHEN);
    }

    public StringBuilder addUnderscore() {
        return this.add(UNDERSCORE);
    }

    public StringBuilder addEqual() {
        return this.add(EQUAL);
    }
}

