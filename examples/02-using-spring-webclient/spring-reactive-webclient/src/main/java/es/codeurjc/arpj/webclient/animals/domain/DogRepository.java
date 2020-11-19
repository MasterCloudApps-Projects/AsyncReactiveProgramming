package es.codeurjc.arpj.webclient.animals.domain;

import reactor.core.publisher.Mono;

public interface DogRepository {

    Mono<Dog> random();
}
