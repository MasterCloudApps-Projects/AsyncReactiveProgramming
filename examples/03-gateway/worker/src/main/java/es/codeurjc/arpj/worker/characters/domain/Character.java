package es.codeurjc.arpj.worker.characters.domain;

public class Character {

    private final CharacterId id;

    private final String name;

    private final CharacterStatus status;

    private final String species;

    private final String image;

    private Character(final Builder builder) {
        this.name    = builder.name;
        this.id      = builder.id;
        this.status  = builder.status;
        this.image   = builder.image;
        this.species = builder.species;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private CharacterId     id;
        private String          name;
        private CharacterStatus status;
        private String          species;
        private String          image;

        private Builder() {
            // Builder
        }

        public Builder id(CharacterId id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder status(CharacterStatus status) {
            this.status = status;
            return this;
        }

        public Builder species(String species) {
            this.species = species;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Character build() {
            return new Character(this);
        }
    }

    public Integer getId() {
        return (id != null) ? id.getValue() : null;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return (status != null) ? status.name() : "";
    }

    public String getSpecies() {
        return species;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", species='" + species + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
