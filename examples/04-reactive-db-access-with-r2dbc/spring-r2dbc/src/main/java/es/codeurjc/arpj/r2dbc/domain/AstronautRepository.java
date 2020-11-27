package es.codeurjc.arpj.r2dbc.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AstronautRepository {

    Flux<Astronaut> findAll();

    Mono<Astronaut> findById(AstronautId id);

    Mono<Astronaut> random();

    Mono<Void> delete(AstronautId id);

    Mono<Astronaut> save(Astronaut astronaut);

    Mono<Void> update(Astronaut astronaut);
}
