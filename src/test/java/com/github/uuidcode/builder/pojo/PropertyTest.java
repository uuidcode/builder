package com.github.uuidcode.builder.pojo;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyTest {
    @Test
    public void getListTypeLong() {
        ArrayList list = new ArrayList<>();
        list.add(1D);
        String type = Property.getListType(list, "issues");
        assertThat(type).isEqualTo("List<Long>");
    }

    @Test
    public void getListTypeMap() {
        ArrayList list = new ArrayList<>();
        list.add(new HashMap());
        String type = Property.getListType(list, "issues");
        assertThat(type).isEqualTo("List<Issue>");
    }
}