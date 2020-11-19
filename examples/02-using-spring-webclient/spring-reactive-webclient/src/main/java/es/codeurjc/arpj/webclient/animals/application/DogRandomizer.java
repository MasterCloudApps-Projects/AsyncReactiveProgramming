package es.codeurjc.arpj.webclient.animals.application;

import es.codeurjc.arpj.webclient.animals.domain.DogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DogRandomizer {

    private final DogRepository repository;

    public Mono<AnimalGroupRandomizerResponse> random() {
        return repository.random().map(AnimalGroupRandomizerResponse::fromDog);
    }
}
