package es.codeurjc.arpj.mvcapi;

import com.github.javafaker.Faker;
import es.codeurjc.arpj.mvcapi.infrastructure.http.UserRequest;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public final class UserRequestMother {

    private static final Faker FAKER = new Faker();

    public static UserRequest random() {

        final var userRequest = new UserRequest();

        userRequest.setId(UUID.randomUUID().toString());
        userRequest.setName(FAKER.funnyName().name());
        userRequest.setSurname(FAKER.gameOfThrones().house());
        userRequest.setEmail(FAKER.internet().emailAddress());

        return userRequest;
    }

    public static UserRequest random(final String id) {

        final var userRequest = new UserRequest();

        userRequest.setId(id);
        userRequest.setName(FAKER.funnyName().name());
        userRequest.setSurname(FAKER.gameOfThrones().house());
        userRequest.setEmail(FAKER.internet().emailAddress());

        return userRequest;
    }
}
