package es.codeurjc.arpj.r2dbc.infrastructure.http;

import es.codeurjc.arpj.r2dbc.application.find.AstronautFinder;
import es.codeurjc.arpj.r2dbc.application.find.AstronautFinderResponse;
import es.codeurjc.arpj.r2dbc.application.search.AstronautSearchCommand;
import es.codeurjc.arpj.r2dbc.application.search.AstronautSearcher;
import es.codeurjc.arpj.r2dbc.application.search.AstronautSearcherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AstronautHttpGetController {

    private static final String GET_ALL_PATH   = "/astronauts";
    private static final String GET_FILTER     = "/astronauts/filter";
    private static final String GET_RANDOM     = "/astronauts/random";
    private static final String GET_BY_ID_PATH = "/astronauts/{id}";

    private final AstronautFinder   finder;
    private final AstronautSearcher searcher;

    @Bean
    RouterFunction<ServerResponse> getAstronautRoutes() {

        return route()

                .GET(GET_ALL_PATH,
                        req -> ok().body(finder.findAll().map(AstronautHttpGetController::toHttpResponse),
                                AstronautHttpResponse.class))

                .GET(GET_FILTER,
                        req -> ok().body(searcher
                                        .search(new AstronautSearchCommand(req.queryParam("name").orElse("")))
                                        .map(AstronautHttpGetController::toHttpResponse),
                                AstronautHttpResponse.class))

                .GET(GET_RANDOM,
                        req -> ok().body(finder.random().map(AstronautHttpGetController::toHttpResponse),
                                AstronautHttpResponse.class))

                .GET(GET_BY_ID_PATH,
                        req -> finder
                                .findById(Long.valueOf(req.pathVariable("id")))
                                .map(AstronautHttpGetController::toHttpResponse)
                                .flatMap(x -> ok().body(Mono.just(x), AstronautHttpResponse.class))
                                .switchIfEmpty(notFound().build()))

                .build();
    }

    private static AstronautHttpResponse toHttpResponse(final AstronautFinderResponse response) {

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

    private static AstronautHttpResponse toHttpResponse(final AstronautSearcherResponse response) {

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
