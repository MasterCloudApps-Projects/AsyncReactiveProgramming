package es.codeurjc.arpj;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FluxTest {

    private static final Faker FAKER = new Faker();

    @Test
    @DisplayName("Test 01: Basic Flux")
    void test() {

        final Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5);

        StepVerifier
                .create(flux)
                .expectNextCount(5)
                .expectComplete()
                .verify();
    }

}
