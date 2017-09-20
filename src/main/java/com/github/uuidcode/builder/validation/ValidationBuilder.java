package com.github.uuidcode.builder.validation;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 */
public class ValidationBuilder<T> {
    private T t;
    private boolean enabled = true;

    public ValidationBuilder() {
    }

    public ValidationBuilder(T t) {
        this(t, true);
    }

    public ValidationBuilder(T t, boolean enabled) {
        this.t = t;
        this.enabled = enabled;
    }

    public static <T> ValidationBuilder<T> of(T t) {
        return new ValidationBuilder<T>(t);
    }

    public static <T> ValidationBuilder<T> of() {
        return new ValidationBuilder<T>();
    }

    public static <T> ValidationBuilder<T> of(T t, Predicate<T> predicate) {
        return new ValidationBuilder<T>(t, predicate.test(t));
    }

    public static <T> ValidationBuilder<T> of(T t, boolean enabled) {
        return new ValidationBuilder<T>(t, enabled);
    }

    public ValidationBuilder<T> add(Predicate<T> predicate, String message) {
        if (this.enabled) {
            return this.add(predicate.test(t), () -> new IllegalStateException(message));
        }

        return this;
    }

    private ValidationBuilder<T> add(boolean predicate, Supplier<RuntimeException> supplier) {
        if (predicate) {
            throw supplier.get();
        }

        return this;
    }

    public ValidationBuilder<T> add(Predicate<T> predicate, Supplier<RuntimeException> supplier) {
        if (this.enabled) {
            return this.add(predicate.test(this.t), supplier);
        }

        return this;
    }
}
