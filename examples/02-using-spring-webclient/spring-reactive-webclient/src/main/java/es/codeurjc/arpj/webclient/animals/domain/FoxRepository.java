package es.codeurjc.arpj.webclient.animals.domain;

import reactor.core.publisher.Mono;

public interface FoxRepository {

    Mono<Fox> random();
}
