package es.codeurjc.arpj.webclient.characters.domain;

import java.util.HashMap;
import java.util.Map;

public enum CharacterStatus {

    ALIVE,
    DEAD,
    UNKNOWN;

    private static final Map<String, CharacterStatus> BY_NAME = new HashMap<>();

    static {
        for (final CharacterStatus item : CharacterStatus.values()) {
            BY_NAME.put(item.name().toLowerCase(), item);
        }
    }

    public static CharacterStatus from(final String value) {
        if (value == null || value.isBlank()) return UNKNOWN;
        return BY_NAME.getOrDefault(value.toLowerCase(), UNKNOWN);
    }
}
