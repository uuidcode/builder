package com.github.uuidcode.util;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.slf4j.Logger;

public class OptionalEx<T> {
    private Optional<T> optional;

    private OptionalEx() {
    }

    public static <T> OptionalEx<T> empty() {
        OptionalEx optionalEx = new OptionalEx();
        optionalEx.optional = Optional.empty();
        return optionalEx;
    }

    public static <T> OptionalEx<T> of(T value) {
        OptionalEx optionalEx = new OptionalEx();
        optionalEx.optional = Optional.of(value);
        return optionalEx;
    }

    public static <T> OptionalEx<T> ofNullable(T value) {
        OptionalEx optionalEx = new OptionalEx();
        optionalEx.optional = Optional.ofNullable(value);
        return optionalEx;
    }

    public T get() {
        return this.optional.get();
    }

    public boolean isPresent() {
        return this.optional.isPresent();
    }

    public void ifPresent(Consumer consumer) {
        this.optional.ifPresent(consumer);
    }

    public OptionalEx<T> filter(Predicate predicate) {
        this.optional = this.optional.filter(predicate);
        return this;
    }

    public static <T> T log(T self, Logger logger, String format, Object... value) {
        if (logger.isDebugEnabled()) {
            if (value.length > 0) {
                logger.debug(format, value);
            } else {
                logger.debug(format, self);
            }
        }

        return self;
    }

    public <U> OptionalEx<U> map(Function<? super T, ? extends U> mapper) {
        U u = this.optional.map(mapper).get();
        return OptionalEx.ofNullable(u);
    }

    public OptionalEx<T> mapJsonDebug(Logger logger, String message, Object value) {
        if (logger.isDebugEnabled()) {
            logger.debug(message, CoreUtil.toJson(value));
        }

        return this;
    }

    public OptionalEx<T> mapDebug(Logger logger, String message, Object... value) {
        if (logger.isDebugEnabled()) {
            logger.debug(message, value);
        }

        return this;
    }

    public <U> OptionalEx<U> mapUnchecked(CheckedFunction<? super T, ? extends U> mapper) {
        Function<? super T, ? extends U> unchecked = CoreUtil.unchecked(mapper);
        U u = this.optional.map(unchecked).get();
        return OptionalEx.ofNullable(u);
    }

    public <U> OptionalEx<U> flatMap(Function<? super T, Optional<U>> mapper) {
        U u = this.optional.flatMap(mapper).get();
        return OptionalEx.ofNullable(u);
    }

    public T orElse(T other) {
        return this.optional.orElse(other);
    }

    public T orElseNull() {
        return this.optional.orElse(null);
    }

    public T orElseGet(Supplier<? extends T> other) {
        return this.optional.orElseGet(other);
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        return this.optional.orElseThrow(exceptionSupplier);
    }

    @Override
    public boolean equals(Object obj) {
        return this.optional.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.optional.hashCode();
    }

    @Override
    public String toString() {
        return this.optional.toString();
    }

    public T orElseList() {
        return this.optional.orElse((T) new ArrayList<>());
    }
}
