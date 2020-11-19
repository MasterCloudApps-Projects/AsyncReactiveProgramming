package es.codeurjc.arpj.webclient.animals.application;

import es.codeurjc.arpj.webclient.animals.domain.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CatRandomizer {

    private final CatRepository repository;

    public Mono<AnimalGroupRandomizerResponse> random() {
        return repository.random().map(AnimalGroupRandomizerResponse::fromCat);
    }
}
