package com.github.uuidcode.util;

import java.lang.reflect.Field;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.FieldNamingPolicy;

public class NameValuePairConverter {
    private Field field;
    private FieldNamingPolicy fieldNamingPolicy;

    public static NameValuePairConverter of() {
        return new NameValuePairConverter();
    }

    public FieldNamingPolicy getFieldNamingPolicy() {
        return this.fieldNamingPolicy;
    }

    public NameValuePairConverter setFieldNamingPolicy(FieldNamingPolicy fieldNamingPolicy) {
        this.fieldNamingPolicy = fieldNamingPolicy;
        return this;
    }

    public Field getField() {
        return this.field;
    }

    public NameValuePairConverter setField(Field field) {
        this.field = field;
        return this;
    }

    public NameValuePair convert(Object object) {
        field.setAccessible(true);

        Object currentObject = null;

        try {
            currentObject = field.get(object);
        } catch (Exception e) {
        }

        if (currentObject != null) {
            String value = CoreUtil.getValue(currentObject);
            String name = this.fieldNamingPolicy.translateName(field);
            return new BasicNameValuePair(name, value);
        }

        return null;
    }
}
