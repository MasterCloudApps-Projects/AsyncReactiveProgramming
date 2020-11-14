package es.codeurjc.arpj.webfluxapi.infrastructure.http;

import es.codeurjc.arpj.webfluxapi.application.create.UserCreator;
import es.codeurjc.arpj.webfluxapi.application.create.UserCreatorCommand;
import es.codeurjc.arpj.webfluxapi.application.find.UserFinder;
import es.codeurjc.arpj.webfluxapi.application.find.UserFinderQuery;
import es.codeurjc.arpj.webfluxapi.application.remove.UserRemover;
import es.codeurjc.arpj.webfluxapi.application.remove.UserRemoverCommand;
import es.codeurjc.arpj.webfluxapi.application.update.UserUpdater;
import es.codeurjc.arpj.webfluxapi.application.update.UserUpdaterCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UserController {

    private static final String USERS_PATH = "/users";
    private static final String USERS_PATH_WITH_ID = "/users/{id}";
    private static final String ID_PATH_VARIABLE = "id";

    private final UserCreator userCreator;
    private final UserFinder userFinder;
    private final UserRemover userRemover;
    private final UserUpdater userUpdater;

    @Bean
    RouterFunction<ServerResponse> routes() {
        return route()

                .GET(USERS_PATH,
                        req -> ok().body(userFinder.findAll()
                                        .map(ufr -> new UserResponse(ufr.id(), ufr.name(), ufr.surname(), ufr.email())),
                                UserResponse.class))

                .GET(USERS_PATH_WITH_ID,
                        req -> userFinder.findById(new UserFinderQuery(req.pathVariable(ID_PATH_VARIABLE)))
                                .map(ufr -> new UserResponse(ufr.id(), ufr.name(), ufr.surname(), ufr.email()))
                                .flatMap(ur -> ok().body(Mono.justOrEmpty(ur), UserResponse.class))
                                .switchIfEmpty(notFound().build()))

                .POST(USERS_PATH,
                        req -> req.bodyToMono(UserRequest.class)
                                .map(r -> new UserCreatorCommand(r.getId(), r.getName(), r.getSurname(), r.getEmail()))
                                .flatMap(userCreator::create)
                                .then(status(HttpStatus.CREATED).build()))

                .PUT(USERS_PATH,
                        req -> req.bodyToMono(UserRequest.class)
                                .map(r -> new UserUpdaterCommand(r.getId(), r.getName(), r.getSurname(), r.getEmail()))
                                .flatMap(userUpdater::update)
                                .then(ok().build()))

                .DELETE(USERS_PATH_WITH_ID,
                        req -> userRemover.remove(new UserRemoverCommand(req.pathVariable(ID_PATH_VARIABLE)))
                                .then(ServerResponse.noContent().build()))

                .build();
    }
}