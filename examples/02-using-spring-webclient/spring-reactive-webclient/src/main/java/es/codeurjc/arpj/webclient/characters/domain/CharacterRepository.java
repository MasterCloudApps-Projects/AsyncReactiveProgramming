package es.codeurjc.arpj.webclient.characters.domain;

import reactor.core.publisher.Mono;

public interface CharacterRepository {

    Mono<Character> random();
}
