package es.codeurjc.arpj.webclient.animals.application;

import es.codeurjc.arpj.webclient.animals.domain.Cat;
import es.codeurjc.arpj.webclient.animals.domain.Dog;
import es.codeurjc.arpj.webclient.animals.domain.Fox;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(builderClassName = "Builder")
public class AnimalGroupRandomizerResponse {

    private final String cat;

    private final String dog;

    private final String fox;

    public static AnimalGroupRandomizerResponse fromCat(final Cat cat) {
        return AnimalGroupRandomizerResponse.builder().cat(cat.getValue()).build();
    }

    public static AnimalGroupRandomizerResponse fromDog(final Dog dog) {
        return AnimalGroupRandomizerResponse.builder().dog(dog.getValue()).build();
    }

    public static AnimalGroupRandomizerResponse fromFox(final Fox fox) {
        return AnimalGroupRandomizerResponse.builder().fox(fox.getValue()).build();
    }
}
