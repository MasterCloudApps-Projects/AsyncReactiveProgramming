package es.codeurjc.arpj.webfluxapi.domain;

public class UniqueIndentifier {

    private final String value;

    public UniqueIndentifier(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "UniqueIndentifier{" +
                "value='" + value + '\'' +
                '}';
    }
}
