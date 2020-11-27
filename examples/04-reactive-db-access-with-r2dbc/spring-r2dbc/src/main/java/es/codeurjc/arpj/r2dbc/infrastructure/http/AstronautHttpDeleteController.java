package es.codeurjc.arpj.r2dbc.infrastructure.http;

import es.codeurjc.arpj.r2dbc.application.remove.AstronautRemover;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AstronautHttpDeleteController {

    private static final String DELETE_PATH = "/astronauts/{id}";

    private final AstronautRemover remover;

    @Bean
    RouterFunction<ServerResponse> deleteAstronautRoutes() {

        return route()

                .DELETE(DELETE_PATH,
                        req -> remover.remove(Long.valueOf(req.pathVariable("id"))).then(noContent().build())
                )

                .build();
    }

}
