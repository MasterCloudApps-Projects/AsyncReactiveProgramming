package es.codeurjc.arpj.r2dbc.infrastructure.http;

import es.codeurjc.arpj.r2dbc.application.create.AstronautCreateCommand;
import es.codeurjc.arpj.r2dbc.application.create.AstronautCreator;
import es.codeurjc.arpj.r2dbc.application.create.AstronautCreatorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.created;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AstronautHttpPostController {

    private static final String POST_PATH = "/astronauts";

    private final AstronautCreator creator;

    @Bean
    RouterFunction<ServerResponse> postAstronautRoutes() {

        return route()

                .POST(POST_PATH,
                        req -> req.bodyToMono(AstronautHttpRequest.class)
                                .map(r -> new AstronautCreateCommand(r.getName(), r.getStatus(), r.getBirthPlace(),
                                        r.getGender(), r.getSpaceFlights(), r.getSpaceWalks(), r.getMissions()))
                                .flatMap(creator::create)
                                .map(AstronautHttpPostController::toHttpResponse)
                                .flatMap(r -> created(URI.create("http://localhost:8080/astronauts/" + r.getId()))
                                        .body(Mono.just(r), AstronautHttpResponse.class)))
                .build();

    }

    private static AstronautHttpResponse toHttpResponse(final AstronautCreatorResponse response) {

        final var httpResponse = new AstronautHttpResponse();

        httpResponse.setId(response.getId());
        httpResponse.setName(response.getName());
        httpResponse.setStatus(response.getStatus());
        httpResponse.setBirthPlace(response.getBirthPlace());
        httpResponse.setGender(response.getGender());
        httpResponse.setSpaceFlights(response.getSpaceFlights());
        httpResponse.setSpaceWalks(response.getSpaceWalks());
        httpResponse.setMissions(response.getMissions());

        return httpResponse;
    }
}
