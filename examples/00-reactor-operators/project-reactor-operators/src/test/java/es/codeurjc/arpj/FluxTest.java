package es.codeurjc.arpj;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Stream;

class FluxTest {

    private static final Faker FAKER = new Faker();

    @Test
    @DisplayName("Test 01: Basic Flux")
    void basic_flux_test() {

        System.out.println("Test 01: Basic Flux");
        System.out.println("=======================================================================================\n");

        final Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5).log();

        StepVerifier
                .create(flux)
                .expectNextCount(5)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test 02: Flux from...")
    void flux_from_test() {

        System.out.println("Test 02: Flux from...");
        System.out.println("=======================================================================================\n");

        final String[] strings = {"one", "two", "three", "four", "five"};
        final Flux<String> stringFlux = Flux.fromArray(strings).log();

        StepVerifier
                .create(stringFlux)
                .expectNextCount(5)
                .expectComplete()
                .verify();

        final List<String> cats = List.of(FAKER.cat().name(), FAKER.cat().name(), FAKER.cat().name());
        final Flux<String> catFlux = Flux.fromIterable(cats).log();

        StepVerifier
                .create(catFlux)
                .expectNextCount(3)
                .expectComplete()
                .verify();

        final Flux<Integer> integerFlux = Flux.range(1, 10).log();

        StepVerifier
                .create(integerFlux)
                .expectNextCount(10)
                .expectComplete()
                .verify();

        final Stream<String> dragons = Stream.of(FAKER.gameOfThrones().dragon(), FAKER.gameOfThrones().dragon(),
                FAKER.gameOfThrones().dragon());
        final Flux<String> dragonFlux = Flux.fromStream(dragons).log();

        StepVerifier
                .create(dragonFlux)
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }
}
