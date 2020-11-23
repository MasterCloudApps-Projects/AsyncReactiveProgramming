package es.codeurjc.arpj.worker.characters.application.random;


import es.codeurjc.arpj.worker.characters.domain.Character;
import es.codeurjc.arpj.worker.characters.domain.CharacterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
public class CharacterRandomizer {

    private static final Random RANDOM = new Random();

    private final CharacterRepository repository;
    private final String              instance;

    public CharacterRandomizer(final CharacterRepository repository,
                               @Value("${eureka.instance.instanceId}") final String instance) {
        this.repository = repository;
        this.instance   = instance;
    }

    public Mono<CharacterRandomizerResponse> random() {
        log.info("### -> Instance=[{}]. Without delay. Regular endpoint: random", instance);
        return repository.random().map(CharacterRandomizer::toRandomizerResponse);
    }

    public Mono<CharacterRandomizerResponse> randomWithDelay() {

        final long delay = RANDOM.nextInt(10) * 100L;
        log.info("### -> Instance=[{}]. Delay in milliseconds: {}", instance, delay);

        return repository.random()
                .delayElement(Duration.ofMillis(delay))
                .map(CharacterRandomizer::toRandomizerResponse);
    }

    public Mono<CharacterRandomizerResponse> randomWithError() {

        final boolean err = RANDOM.nextInt(10) > 5;
        log.info("### -> Instance=[{}]. Generated error: {}", instance, err);

        return (err) ? Mono.error(Exception::new) : repository.random().map(CharacterRandomizer::toRandomizerResponse);
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
