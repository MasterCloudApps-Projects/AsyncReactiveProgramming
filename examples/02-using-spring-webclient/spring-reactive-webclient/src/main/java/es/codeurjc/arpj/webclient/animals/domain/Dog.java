package es.codeurjc.arpj.webclient.animals.domain;

public class Dog {

    private final String value;

    public Dog(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "value='" + value + '\'' +
                '}';
    }
}
