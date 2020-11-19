package es.codeurjc.arpj.webclient.animals.application;

import es.codeurjc.arpj.webclient.animals.domain.FoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FoxRandomizer {

    private final FoxRepository repository;

    public Mono<AnimalGroupRandomizerResponse> random() {
        return repository.random().map(AnimalGroupRandomizerResponse::fromFox);
    }

}
