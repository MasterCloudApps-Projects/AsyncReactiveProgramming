package es.codeurjc.arpj.webclient.characters.domain;

public class CharacterId {

    private final int value;

    public CharacterId(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "CharacterId{" +
                "value=" + value +
                '}';
    }
}
