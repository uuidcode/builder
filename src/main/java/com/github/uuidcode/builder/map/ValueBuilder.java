package com.github.uuidcode.builder.map;

import java.util.function.Function;

public class ValueBuilder<T, K> {
    private Function<T, K> mapper;

    public static <T, K> ValueBuilder<T, K> of(Function<T, K> mapper) {
        return new ValueBuilder<T, K>()
            .setMapper(mapper);
    }

    public Function<T, K> getMapper() {
        return this.mapper;
    }

    public ValueBuilder<T, K> setMapper(Function<T, K> mapper) {
        this.mapper = mapper;
        return this;
    }
}
