package es.codeurjc.arpj.webclient.animals.application;

import es.codeurjc.arpj.webclient.animals.domain.CatRepository;
import es.codeurjc.arpj.webclient.animals.domain.DogRepository;
import es.codeurjc.arpj.webclient.animals.domain.FoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnimalGroupRandomizer {

    private final CatRepository catRepository;
    private final DogRepository dogRepository;
    private final FoxRepository foxRepository;

    public Mono<AnimalGroupRandomizerResponse> random() {
        return Mono.zip(catRepository.random(), dogRepository.random(), foxRepository.random())
                .map(t -> AnimalGroupRandomizerResponse.builder()
                        .cat(t.getT1().getValue())
                        .dog(t.getT2().getValue())
                        .fox(t.getT3().getValue())
                        .build());
    }
}
