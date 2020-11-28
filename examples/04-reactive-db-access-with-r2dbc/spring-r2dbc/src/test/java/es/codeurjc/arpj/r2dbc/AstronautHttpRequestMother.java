package es.codeurjc.arpj.r2dbc;

import com.github.javafaker.Faker;
import es.codeurjc.arpj.r2dbc.infrastructure.http.AstronautHttpRequest;
import es.codeurjc.arpj.r2dbc.shared.domain.Gender;
import es.codeurjc.arpj.r2dbc.shared.domain.Status;
import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public final class AstronautHttpRequestMother {

    private static final Faker  FAKER  = new Faker();
    private static final Random RANDOM = new Random();

    public static AstronautHttpRequest random() {

        final var request = new AstronautHttpRequest();
        basicRandomRequest(request);

        return request;
    }

    public static AstronautHttpRequest random(final Long id) {

        final var request = new AstronautHttpRequest();
        request.setId(id);
        basicRandomRequest(request);

        return request;
    }

    private static void basicRandomRequest(AstronautHttpRequest request) {
        request.setName(FAKER.funnyName().name());
        request.setBirthPlace(FAKER.country().capital());
        request.setMissions(FAKER.lordOfTheRings().location());
        request.setSpaceFlights(FAKER.number().numberBetween(0, 50));
        request.setSpaceWalks(FAKER.number().numberBetween(0, 100));
        request.setStatus(Status.values()[RANDOM.nextInt(Status.values().length)].getValue());
        request.setGender(Gender.values()[RANDOM.nextInt(Gender.values().length)].getValue());
    }

}
