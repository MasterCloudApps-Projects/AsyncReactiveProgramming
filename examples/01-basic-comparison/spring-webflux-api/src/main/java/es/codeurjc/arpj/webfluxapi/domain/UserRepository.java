package es.codeurjc.arpj.webfluxapi.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Flux<User> findAll();

    Mono<User> findOne(UserId id);

    Mono<Void> save(User user);

    Mono<Void> update(User user);

    Mono<Void> delete(UserId id);
}
