package es.codeurjc.arpj.r2dbc.shared.domain;

import java.util.HashMap;
import java.util.Map;

public enum Status {

    ACTIVE("A", "Active"),
    RETIRED("R", "Retired"),
    DECEASED("D", "Deceased"),
    MANAGEMENT("M", "Management");

    private static final Map<String, Status> BY_VALUE = new HashMap<>();
    private static final Map<String, Status> BY_NAME  = new HashMap<>();

    static {

        for (final Status item : Status.values()) {
            BY_VALUE.put(item.getValue().toLowerCase(), item);
        }

        for (final Status item : Status.values()) {
            BY_NAME.put(item.getName().toLowerCase(), item);
        }
    }

    private final String value;

    private final String name;

    Status(String value, String name) {
        this.value = value;
        this.name  = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static Status from(final String value) {
        if (value == null || value.isBlank()) return null;
        return BY_VALUE.getOrDefault(value.toLowerCase(), null);
    }

    public static Status fromName(final String name) {
        if (name == null || name.isBlank()) return null;
        return BY_NAME.getOrDefault(name.toLowerCase(), null);
    }
}
