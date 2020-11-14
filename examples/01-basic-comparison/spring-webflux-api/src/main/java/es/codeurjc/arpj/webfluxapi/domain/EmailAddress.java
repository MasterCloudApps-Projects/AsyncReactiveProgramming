package es.codeurjc.arpj.webfluxapi.domain;

public class EmailAddress {

    private final String value;

    public EmailAddress(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "EmailAddress{" +
                "value='" + value + '\'' +
                '}';
    }
}
