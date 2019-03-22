package com.github.uuidcode.builder.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;
import org.slf4j.Logger;

import com.github.uuidcode.builder.domain.Person;
import com.github.uuidcode.util.CoreUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class MapBuilderTest {
    protected static Logger logger = getLogger(MapBuilderTest.class);

    @Test
    public void list() {
        List<Person> list = new ArrayList<>();
        list.add(Person.of().setName(CoreUtil.createUUID()).setPersonId(1));
        list.add(Person.of().setName(CoreUtil.createUUID()).setPersonId(2));

        Map<Integer, Person> map = KeyBuilder.of(Person::getPersonId)
            .setValue(Function.identity())
            .build(list);

        if (logger.isDebugEnabled()) {
            logger.debug(">>> builder map: {}", CoreUtil.toJson(map));
        }
    }

    @Test
    public void buildFromSet() {
        Set<Person> set = new HashSet<>();
        set.add(Person.of().setName(CoreUtil.createUUID()).setPersonId(1));
        set.add(Person.of().setName(CoreUtil.createUUID()).setPersonId(2));

        Map<Integer, String> map = KeyBuilder.of(Person::getPersonId)
            .setValue(Person::getName)
            .build(set);

        if (logger.isDebugEnabled()) {
            logger.debug(">>> builder map: {}", CoreUtil.toJson(map));
        }
    }

    @Test
    public void toMap() {
        Set<Person> set = new HashSet<>();
        set.add(Person.of().setName(CoreUtil.createUUID()).setPersonId(1));
        set.add(Person.of().setName(CoreUtil.createUUID()).setPersonId(2));

        Map<Integer, String> map = set.stream()
            .collect(Collectors.toMap(Person::getPersonId, Person::getName));

        if (logger.isDebugEnabled()) {
            logger.debug(">>> builder map: {}", CoreUtil.toJson(map));
        }
    }
}