package es.codeurjc.arpj.webclient.animals.infrastructure.http_in;

import es.codeurjc.arpj.webclient.animals.application.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnimalController {

    private static final String ANIMALS_PATH = "/animals/random";
    private static final String CATS_PATH = "/cats/random";
    private static final String DOGS_PATH = "/dogs/random";
    private static final String FOXES_PATH = "/foxes/random";

    private final AnimalGroupRandomizer randomizer;
    private final CatRandomizer catRandomizer;
    private final DogRandomizer dogRandomizer;
    private final FoxRandomizer foxRandomizer;

    @Bean
    RouterFunction<ServerResponse> animalGroupRoutes() {

        return route()

                .GET(ANIMALS_PATH,
                        req -> ok().body(randomizer.random().map(AnimalController::toHttpResponse),
                                AnimalGroupHttpResponse.class))

                .GET(CATS_PATH,
                        req -> ok().body(catRandomizer.random().map(AnimalController::toHttpResponse),
                                AnimalGroupHttpResponse.class))

                .GET(DOGS_PATH,
                        req -> ok().body(dogRandomizer.random().map(AnimalController::toHttpResponse),
                                AnimalGroupHttpResponse.class))

                .GET(FOXES_PATH,
                        req -> ok().body(foxRandomizer.random().map(AnimalController::toHttpResponse),
                                AnimalGroupHttpResponse.class))

                .build();
    }

    private static AnimalGroupHttpResponse toHttpResponse(final AnimalGroupRandomizerResponse response) {

        return AnimalGroupHttpResponse.builder()
                .cat(response.getCat())
                .dog(response.getDog())
                .fox(response.getFox())
                .build();
    }

}
