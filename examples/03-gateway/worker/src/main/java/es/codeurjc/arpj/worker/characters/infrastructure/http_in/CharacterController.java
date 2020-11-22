package es.codeurjc.arpj.worker.characters.infrastructure.http_in;

import es.codeurjc.arpj.worker.characters.application.random.CharacterRandomizer;
import es.codeurjc.arpj.worker.characters.application.random.CharacterRandomizerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CharacterController {

    private static final String CHARACTERS_PATH      = "/characters/random";
    private static final String CHARACTERS_LIST_PATH = "/characters/random-list";

    private final CharacterRandomizer randomizer;

    @Bean
    RouterFunction<ServerResponse> characterRoutes() {

        return route()

                .GET(CHARACTERS_PATH,
                        req -> ok().body(randomizer.random().map(CharacterController::toHttpResponse),
                                CharacterHttpResponse.class))

                .GET(CHARACTERS_LIST_PATH,
                        req -> ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
                                randomizer
                                        .randomList()
                                        .doOnNext(System.out::println)
                                        .map(CharacterController::toHttpResponse),
                                CharacterHttpResponse.class))
                .build();
    }

    private static CharacterHttpResponse toHttpResponse(final CharacterRandomizerResponse response) {

        return CharacterHttpResponse.builder()
                .id(response.getId())
                .name(response.getName())
                .status(response.getStatus())
                .species(response.getSpecies())
                .image(response.getImage())
                .build();
    }
}
