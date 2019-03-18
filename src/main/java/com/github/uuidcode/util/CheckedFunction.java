package com.github.uuidcode.util;

public interface CheckedFunction<T, R> {
    R apply(T t) throws Throwable;
}
