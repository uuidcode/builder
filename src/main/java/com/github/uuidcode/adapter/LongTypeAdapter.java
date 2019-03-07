package com.github.uuidcode.adapter;

import java.io.IOException;
import java.math.BigDecimal;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class LongTypeAdapter extends TypeAdapter<Long> {
    @Override
    public void write(JsonWriter out, Long value) throws IOException {
        out.value(value);
    }

    @Override
    public Long read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        String result = in.nextString();

        try {
            if (result.contains("E")) {
                return new BigDecimal(result).longValue();
            }

            return Long.parseLong(result.replaceAll("\\,", "").trim(), 10);
        } catch (Exception e) {
            return null;
        }
    }
}
