package es.codeurjc.arpj.r2dbc.domain;

import es.codeurjc.arpj.r2dbc.shared.domain.Gender;
import es.codeurjc.arpj.r2dbc.shared.domain.Status;

public class Astronaut {

    private final AstronautId id;

    private final String name;

    private final Status status;

    private final String birthPlace;

    private final Gender gender;

    private final int spaceFlights;

    private final int spaceWalks;

    private final String missions;

    private Astronaut(final Builder builder) {
        id           = builder.id;
        name         = builder.name;
        status       = builder.status;
        birthPlace   = builder.birthPlace;
        gender       = builder.gender;
        spaceFlights = builder.spaceFlights;
        spaceWalks   = builder.spaceWalks;
        missions     = builder.missions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private AstronautId id;
        private String      name;
        private Status      status;
        private String      birthPlace;
        private Gender      gender;
        private int         spaceFlights;
        private int         spaceWalks;
        private String      missions;

        private Builder() {
            // Builder
        }

        public Builder id(AstronautId id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder birthPlace(String birthPlace) {
            this.birthPlace = birthPlace;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder spaceFlights(int spaceFlights) {
            this.spaceFlights = spaceFlights;
            return this;
        }

        public Builder spaceWalks(int spaceWalks) {
            this.spaceWalks = spaceWalks;
            return this;
        }

        public Builder missions(String missions) {
            this.missions = missions;
            return this;
        }

        public Astronaut build() {
            return new Astronaut(this);
        }
    }

    public Long getId() {
        return this.id != null ? this.id.getValue() : null;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public Gender getGender() {
        return gender;
    }

    public int getSpaceFlights() {
        return spaceFlights;
    }

    public int getSpaceWalks() {
        return spaceWalks;
    }

    public String getMissions() {
        return missions;
    }

    @Override
    public String toString() {
        return "Astronaut{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", birthPlace='" + birthPlace + '\'' +
                ", gender=" + gender +
                ", spaceFlights=" + spaceFlights +
                ", spaceWalks=" + spaceWalks +
                ", missions='" + missions + '\'' +
                '}';
    }
}
