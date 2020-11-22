package es.codeurjc.arpj.worker.characters.domain;

import reactor.core.publisher.Mono;

public interface CharacterRepository {

    Mono<Character> random();
}
