package es.codeurjc.arpj;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

class MonoTest {

    private static final Faker FAKER = new Faker();

    @Test
    @DisplayName("Test 01: Basic Mono")
    void basic_mono_test() {

        final String myCharacter = FAKER.backToTheFuture().character();
        final Mono<String> myMono = Mono.just(myCharacter);

        StepVerifier
                .create(myMono)
                .expectNext(myCharacter)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test 02: Just or Empty")
    void mono_just_or_empty_test() {

        final String myCharacter = FAKER.gameOfThrones().character();

        final Mono<String> myMono = Mono.justOrEmpty(myCharacter);
        final Mono<String> myEmptyMono = Mono.justOrEmpty(Optional.empty());
        final Mono<String> myNullMono = Mono.justOrEmpty(null);

        StepVerifier
                .create(myMono)
                .expectNext(myCharacter)
                .expectComplete()
                .verify();

        StepVerifier
                .create(myEmptyMono)
                .expectComplete()
                .verify();

        StepVerifier
                .create(myNullMono)
                .expectComplete()
                .verify();

        /*
        final Mono<String> myExplodingMono = Mono.just(null);

        StepVerifier
                .create(myExplodingMono)
                .expectComplete()
                .verify();
        */
    }

}
