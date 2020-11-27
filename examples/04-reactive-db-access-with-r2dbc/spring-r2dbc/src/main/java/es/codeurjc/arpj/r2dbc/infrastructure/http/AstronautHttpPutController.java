package es.codeurjc.arpj.r2dbc.infrastructure.http;

import es.codeurjc.arpj.r2dbc.application.update.AstronautUpdateCommand;
import es.codeurjc.arpj.r2dbc.application.update.AstronautUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AstronautHttpPutController {

    private static final String PUT_PATH = "/astronauts";

    private final AstronautUpdater updater;

    @Bean
    RouterFunction<ServerResponse> putAstronautRoutes() {

        return route()

                .PUT(PUT_PATH,
                        req -> req.bodyToMono(AstronautHttpRequest.class)
                                .map(r -> new AstronautUpdateCommand(r.getId(), r.getName(), r.getStatus(),
                                        r.getBirthPlace(), r.getGender(), r.getSpaceFlights(), r.getSpaceWalks(),
                                        r.getMissions()))
                                .flatMap(x -> ok().build(updater.update(x))))
                .build();

    }
}
