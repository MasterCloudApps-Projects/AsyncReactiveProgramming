package es.codeurjc.arpj.r2dbc.domain;

public class AstronautId {

    private final Long value;

    public AstronautId(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "AstronautId{" +
                "value=" + value +
                '}';
    }
}
