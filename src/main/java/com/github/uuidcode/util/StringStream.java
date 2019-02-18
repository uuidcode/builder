package com.github.uuidcode.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.uuidcode.util.CoreUtil.AMPERSAND;
import static com.github.uuidcode.util.CoreUtil.COMMA;
import static com.github.uuidcode.util.CoreUtil.DOT;
import static com.github.uuidcode.util.CoreUtil.EQUAL;
import static com.github.uuidcode.util.CoreUtil.HYPHEN;
import static com.github.uuidcode.util.CoreUtil.LINE_FEED;
import static com.github.uuidcode.util.CoreUtil.QUESTION_MARK;
import static com.github.uuidcode.util.CoreUtil.SEMICOLON;
import static com.github.uuidcode.util.CoreUtil.SHARP;
import static com.github.uuidcode.util.CoreUtil.SLASH;
import static com.github.uuidcode.util.CoreUtil.SPACE;
import static com.github.uuidcode.util.CoreUtil.UNDERSCORE;
import static com.github.uuidcode.util.CoreUtil.VERTICAL_BAR;
import static com.github.uuidcode.util.CoreUtil.lineSeparator;

public class StringStream {
    public static final String EMPTY = "";
    private Stream.Builder<String> builder = Stream.builder();
    private List<IntermediateOperation> intermediateOperationList = new ArrayList<>();
    private Function<String, String> finisher;

    public Function<String, String> getFinisher() {
        return this.finisher;
    }

    public StringStream setFinisher(Function<String, String> finisher) {
        this.finisher = finisher;
        return this;
    }

    public StringStream setFinisher(boolean isTrue, Function<String, String> finisher) {
        if (isTrue) {
            this.setFinisher(finisher);
        }

        return this;
    }

    public List<IntermediateOperation> getIntermediateOperationList() {
        return this.intermediateOperationList;
    }

    public StringStream setIntermediateOperationList(List<IntermediateOperation> intermediateOperationList) {
        this.intermediateOperationList = intermediateOperationList;
        return this;
    }

    public static StringStream of() {
        return new StringStream();
    }

    public static StringStream of(List<String> list) {
        return of(list, Function.identity());
    }

    public static <T> StringStream of(List<T> list, Function<T, String> mapper) {
        StringStream stringStream = StringStream.of();
        add(stringStream, list, mapper);
        return stringStream;
    }

    private static <T> void add(StringStream stringStream, List<T> list, Function<T, String> mapper) {
        if (list != null) {
            list.stream().map(mapper).forEach(stringStream::add);
        }
    }

    public <T> StringStream add(List<T> list, Function<T, String> mapper) {
        list.stream().map(mapper).forEach(this::add);
        return this;
    }

    public static StringStream split(String text, String regex) {
        StringStream stringStream = StringStream.of();

        if (text != null) {
            String[] result = text.split(regex);
            Arrays.stream(result)
                .forEach(stringStream::add);
        }

        return stringStream;
    }

    public static StringStream of(Object object) {
        return new StringStream().add(object);
    }

    public StringStream add(List<String> list) {
        add(this, list, Function.identity());
        return this;
    }

    public StringStream addSpace() {
        return this.add(SPACE);
    }

    public StringStream addEmpty() {
        return this.add(EMPTY);
    }

    public StringStream addComma() {
        return this.add(COMMA);
    }

    public StringStream addWithWrappedBrace(String name) {
         return this.addLeftBrace()
            .add(name)
            .addRightBrace();
    }

    public StringStream addSpaceEqualSpace() {
        return this.add(SPACE)
            .add(EQUAL)
            .add(SPACE);
    }

    public StringStream addSharp() {
        return this.add(SHARP);
    }

    public StringStream addSlash() {
        return this.add(SLASH);
    }

    public StringStream addEqual() {
        return this.add(EQUAL);
    }

    public StringStream addDot() {
        return this.add(DOT);
    }

    public StringStream addLeftParenthesis() {
        return this.add(CoreUtil.leftParenthesis());
    }

    public StringStream addRightParenthesis() {
        return this.add(CoreUtil.rightParenthesis());
    }

    public StringStream addLeftBrace() {
        return this.add(CoreUtil.leftBrace());
    }

    public StringStream addRightAngleBracket() {
        return this.add(CoreUtil.rightAngleBracket());
    }

    public StringStream addLeftAngleBracket() {
        return this.add(CoreUtil.leftAngleBracket());
    }

    public StringStream addRightBrace() {
        return this.add(CoreUtil.rightBrace());
    }

    public StringStream addSemicolon() {
        return this.add(SEMICOLON);
    }

    public StringStream add(Object object) {
        if (object != null) {
            this.builder.add(object.toString());
        }

        return this;
    }

    public StringStream map(Function<String, String> mapper) {
        this.intermediateOperationList.add(IntermediateOperation.map(mapper));
        return this;
    }

    public StringStream appendIndent() {
        return this.appendIndent(1);
    }

    public StringStream appendIndent(int size) {
        IntermediateOperation map = IntermediateOperation.map(i -> CoreUtil.indent(size) + i);
        this.intermediateOperationList.add(map);
        return this;
    }

    public StringStream wrapWithDoubleQuotation() {
        this.map(CoreUtil::wrapWithDoubleQuote);
        return this;
    }

    public StringStream filter(Predicate<String> predicate) {
        this.intermediateOperationList.add(IntermediateOperation.filter(predicate));
        return this;
    }

    public StringStream skip(Long index) {
        this.intermediateOperationList.add(IntermediateOperation.skip(index));
        return this;
    }

    public StringStream skip(Integer index) {
        return this.skip(new Long(index));
    }

    public StringStream add(Supplier<Boolean> supplier, Object value) {
        return this.add(supplier.get(), value);
    }

    public StringStream addNotEmpty(Object object) {
        if (object == null) {

        } else {
            String value = object.toString();
            return this.add(CoreUtil.isNotEmpty(value), value);
        }

        return this;
    }

    public StringStream add(boolean test, List<String> list) {
        return this.add(test ? list : null);
    }

    public StringStream add(boolean test, Object value) {
        return this.add(test ? value : null);
    }

    public StringStream add(boolean test, Object value1, Object value2) {
        return this.add(test ? value1 : value2);
    }

    public <T> StringStream add(boolean test, Supplier<String> supplier) {
        return this.add(test ? supplier.get() : null);
    }

    public String joining() {
        return this.joining(EMPTY);
    }

    public String joining(CharSequence delimiter) {
        Stream<String> stream = this.builder.build();

        if (this.intermediateOperationList != null) {
            for (IntermediateOperation intermediateOperation : intermediateOperationList) {
                stream = intermediateOperation.run(stream);
            }
        }

        String result = stream.collect(Collectors.joining(delimiter));

        if (this.finisher != null) {
            return this.finisher.apply(result);
        }

        return result;
    }

    public StringStream joiningAndThen(Function<Integer, String> delimiter) {
        return StringStream.of().add(this.joining(delimiter));
    }

    public String joining(Function<Integer, String> delimiter) {
        List<String> list = this.builder.build().collect(Collectors.toList());
        return IntStream.range(0, list.size())
            .mapToObj(i -> {
                String item = list.get(i);

                if (i == list.size() - 1) {
                    return item;
                }

                return item + delimiter.apply(i);
            })
            .collect(Collectors.joining());
    }

    public String joiningWithComma() {
        return this.joining(COMMA);
    }

    public String joiningWithCommaAndSpace() {
        return this.joining(COMMA + SPACE);
    }

    public String joiningWithCommaAndLineSeparator() {
        return this.joining(COMMA + lineSeparator());
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
        return this.joining(CoreUtil.lineSeparator());
    }

    public String joiningWithLineFeed() {
        return this.joining(LINE_FEED);
    }

    public StringStream joiningAndThen() {
        return StringStream.of().add(this.joining());
    }

    public StringStream joiningAndThen(CharSequence delimiter) {
        return StringStream.of().add(this.joining(delimiter));
    }

    public StringStream joiningWithCommaAndThen() {
        return StringStream.of().add(this.joiningWithComma());
    }

    public StringStream joiningWithSpaceAndThen() {
        return StringStream.of().add(this.joiningWithSpace());
    }

    public StringStream joiningWithDotAndThen() {
        return StringStream.of().add(this.joiningWithDot());
    }

    public StringStream joiningWithUnderscoreAndThen() {
        return StringStream.of().add(this.joiningWithUnderscore());
    }

    public StringStream joiningWithHyphenAndThen() {
        return StringStream.of().add(this.joiningWithHyphen());
    }

    public StringStream joiningWithVerticalBarAndThen() {
        return StringStream.of().add(this.joiningWithVerticalBar());
    }

    public StringStream joiningWithSlashAndThen() {
        return StringStream.of().add(this.joiningWithSlash());
    }

    public StringStream joiningWithNewLineAndThen() {
        return StringStream.of().add(this.joiningWithNewLine());
    }
}