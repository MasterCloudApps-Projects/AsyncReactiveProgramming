package es.codeurjc.arpj.worker.characters.application.random;


import com.netflix.appinfo.EurekaInstanceConfig;
import es.codeurjc.arpj.worker.characters.domain.Character;
import es.codeurjc.arpj.worker.characters.domain.CharacterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
public class CharacterRandomizer {

    private static final Random RANDOM = new Random();

    private final CharacterRepository  repository;
    private final EurekaInstanceConfig eurekaInstance;

    public CharacterRandomizer(final CharacterRepository repository,
                               final EurekaInstanceConfig eurekaInstance) {
        this.repository     = repository;
        this.eurekaInstance = eurekaInstance;
    }

    public Mono<CharacterRandomizerResponse> random() {
        log.info("### -> Instance=[{}]. Without delay. Regular endpoint: random", eurekaInstance.getInstanceId());
        return repository.random().map(CharacterRandomizer::toRandomizerResponse);
    }

    public Mono<CharacterRandomizerResponse> randomWithDelay() {

        final long delay = RANDOM.nextInt(10) * 100L;
        log.info("### -> Instance=[{}]. Delay in milliseconds: {}", eurekaInstance.getInstanceId(), delay);

        return repository.random()
                .delayElement(Duration.ofMillis(delay))
                .map(CharacterRandomizer::toRandomizerResponse);
    }

    public Mono<CharacterRandomizerResponse> randomWithError() {

        final boolean err = RANDOM.nextInt(10) > 5;
        log.info("### -> Instance=[{}]. Generated error: {}", eurekaInstance.getInstanceId(), err);

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
