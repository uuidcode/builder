package com.github.uuidcode.builder.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class MapBuilder<T, K, V> {
    private KeyBuilder<T, K> keyBuilder;
    private ValueBuilder<T, V> valueBuilder;

    public ValueBuilder<T, V> getValueBuilder() {
        return this.valueBuilder;
    }

    public MapBuilder<T, K, V> setValueBuilder(ValueBuilder<T, V> valueBuilder) {
        this.valueBuilder = valueBuilder;
        return this;
    }

    public KeyBuilder<T, K> getKeyBuilder() {
        return this.keyBuilder;
    }

    public MapBuilder<T, K, V> setKeyBuilder(KeyBuilder<T, K> keyBuilder) {
        this.keyBuilder = keyBuilder;
        return this;
    }

    public Map<K, V> build(Set<T> set) {
        set = ofNullable(set).orElse(new HashSet<>());
        List<T> list = new ArrayList<>(set);
        return build(list);
    }

    public Map<K, V> build(List<T> list) {
        if (keyBuilder == null) {
            return new HashMap<>();
        }

        if (valueBuilder == null) {
            return new HashMap<>();
        }

        return ofNullable(list)
            .orElse(new ArrayList<>())
            .stream()
            .collect(Collectors.toMap(this.keyBuilder.getMapper(), this.valueBuilder.getMapper()));
    }
}
