package com.github.uuidcode.builder.map;

import java.util.Objects;
import java.util.function.Function;

public class KeyBuilder<T, K> {
    private Function<T, K> mapper;

    public static <T, K> KeyBuilder<T, K> of(Function<T, K> mapper) {
        return new KeyBuilder<T, K>()
            .setMapper(mapper);
    }

    public Function<T, K> getMapper() {
        return this.mapper;
    }

    public KeyBuilder<T, K> setMapper(Function<T, K> mapper) {
        Objects.requireNonNull(mapper);
        this.mapper = mapper;
        return this;
    }

    public <V> MapBuilder<T, K, V> setValue(Function<T, V> mapper) {
        Objects.requireNonNull(mapper);

        return new MapBuilder<T, K, V>()
            .setKeyBuilder(this)
            .setValueBuilder(ValueBuilder.of(mapper));
    }
}
