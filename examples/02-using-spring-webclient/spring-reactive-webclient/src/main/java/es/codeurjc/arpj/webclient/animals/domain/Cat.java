package es.codeurjc.arpj.webclient.animals.domain;

public class Cat {

    final String value;

    public Cat(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "value='" + value + '\'' +
                '}';
    }
}
