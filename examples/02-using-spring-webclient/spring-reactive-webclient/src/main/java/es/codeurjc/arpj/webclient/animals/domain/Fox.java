package es.codeurjc.arpj.webclient.animals.domain;

public class Fox {

    final String value;

    public Fox(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Fox{" +
                "value='" + value + '\'' +
                '}';
    }
}
