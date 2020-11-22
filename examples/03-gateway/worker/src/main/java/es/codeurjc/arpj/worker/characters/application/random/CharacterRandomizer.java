package es.codeurjc.arpj.worker.characters.application.random;


import es.codeurjc.arpj.worker.characters.domain.Character;
import es.codeurjc.arpj.worker.characters.domain.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CharacterRandomizer {

    private final CharacterRepository repository;

    public Mono<CharacterRandomizerResponse> random() {
        return repository.random().map(CharacterRandomizer::toRandomizerResponse);
    }

    public Flux<CharacterRandomizerResponse> randomList() {
        return Flux.interval(Duration.ofSeconds(2))
                .flatMap(f -> repository.random()).map(CharacterRandomizer::toRandomizerResponse);
    }

    private static CharacterRandomizerResponse toRandomizerResponse(final Character character) {

        return CharacterRandomizerResponse.builder()
                .id(character.getId())
                .name(character.getName())
                .status(character.getStatus())
                .species(character.getSpecies())
                .image(character.getImage())
                .build();
    }
}
