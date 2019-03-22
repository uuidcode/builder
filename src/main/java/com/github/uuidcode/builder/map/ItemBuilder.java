package com.github.uuidcode.builder.map;

import java.util.function.Function;

public class ItemBuilder<T, K> {
    private Function<T, K> mapper;

    public static <T, K> ItemBuilder<T, K> of(Function<T, K> mapper) {
        return new ItemBuilder<T, K>()
            .setMapper(mapper);
    }

    public Function<T, K> getMapper() {
        return this.mapper;
    }

    public ItemBuilder<T, K> setMapper(Function<T, K> mapper) {
        this.mapper = mapper;
        return this;
    }
}
