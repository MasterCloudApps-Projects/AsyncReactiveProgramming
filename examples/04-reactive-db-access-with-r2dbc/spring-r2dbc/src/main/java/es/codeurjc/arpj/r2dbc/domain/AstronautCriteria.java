package es.codeurjc.arpj.r2dbc.domain;

public class AstronautCriteria {

    private final String name;

    public AstronautCriteria(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AstronautCriteria{" +
                "name='" + name + '\'' +
                '}';
    }
}
