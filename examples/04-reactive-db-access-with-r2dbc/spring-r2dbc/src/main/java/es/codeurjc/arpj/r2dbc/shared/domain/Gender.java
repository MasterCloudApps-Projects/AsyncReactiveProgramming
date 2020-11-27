package es.codeurjc.arpj.r2dbc.shared.domain;

import java.util.HashMap;
import java.util.Map;

public enum Gender {

    FEMALE("F", "Female"),
    MALE("M", "Male");

    private static final Map<String, Gender> BY_VALUE = new HashMap<>();
    private static final Map<String, Gender> BY_NAME  = new HashMap<>();

    static {

        for (final Gender item : Gender.values()) {
            BY_VALUE.put(item.value.toLowerCase(), item);
        }

        for (final Gender item : Gender.values()) {
            BY_NAME.put(item.getName().toLowerCase(), item);
        }
    }

    private final String value;

    private final String name;

    Gender(String value, String name) {
        this.value = value;
        this.name  = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static Gender from(final String value) {
        if (value == null || value.isBlank()) return null;
        return BY_VALUE.getOrDefault(value.toLowerCase(), null);
    }

    public static Gender fromName(final String name) {
        if (name == null || name.isBlank()) return null;
        return BY_NAME.getOrDefault(name.toLowerCase(), null);
    }
}
