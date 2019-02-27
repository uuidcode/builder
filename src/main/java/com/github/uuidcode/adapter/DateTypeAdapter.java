package com.github.uuidcode.adapter;

import java.io.IOException;
import java.util.Date;

import com.github.uuidcode.util.CoreUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class DateTypeAdapter extends TypeAdapter<Date> {
    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        String date = CoreUtil.formatDatetime(value);
        out.value(date);
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        String value = in.nextString();
        return CoreUtil.parseDateTime(value);
    }
}
