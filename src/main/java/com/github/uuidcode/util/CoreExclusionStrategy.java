package com.github.uuidcode.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

class CoreExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        if (fieldAttributes.getDeclaredClass().equals(Class.class)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
