package es.codeurjc.arpj.webfluxapi.domain;

public class User {

    private final UserId id;

    private final String name;

    private final String surname;

    private final EmailAddress emailAddress;

    public User(final UserId id, final String name, final String surname, final EmailAddress emailAddress) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return (id.getValue() != null) ? id.getValue() : null;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmailAddress() {
        return emailAddress.getValue();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", emailAddress=" + getEmailAddress() +
                '}';
    }
}
