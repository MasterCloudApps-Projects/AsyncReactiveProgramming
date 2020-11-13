package es.codeurjc.arpj.mvcapi.domain;

public class UserId {

    private final UniqueIndentifier value;

    public UserId(final String value) {
        this.value = new UniqueIndentifier(value);
    }

    public String getValue() {
        return value.getValue();
    }
}
