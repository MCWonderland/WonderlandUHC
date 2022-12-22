package org.mcwonderland.uhc.model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Registry<E> {
    private Class clazz;

    public Registry(Class clazz) {
        this.clazz = clazz;
    }

    public final void register() {
        getObjects();
    }

    private List<E> getObjects() {
        Field[] fields = this.getClass().getDeclaredFields();

        return Arrays.stream(fields)
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(o -> o.getClass().isAssignableFrom(clazz))
                .map(o -> (E) o)
                .collect(Collectors.toList());
    }
}
