package es.codeurjc.arpj;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

import static es.codeurjc.arpj.TestUtils.printTestLine;

class PublisherProbeTest {

    private static final Faker FAKER = new Faker();

    @Test
    @DisplayName("Test 01: Publisher Probe. Basic")
    void basic_publisher_probe_1() {

        System.out.println("Test 01: Publisher Probe. Basic");
        printTestLine();

        final Mono<String> myDog = myComplexOperation(Mono.just(FAKER.dog().name()),
                Mono.just(FAKER.animal().name())).log();

        StepVerifier.create(myDog)
                .expectNextCount(1)
                .expectComplete()
                .verify();

        PublisherProbe<String> probe = PublisherProbe.of(Mono.just(FAKER.dog().name()));

        final Mono<String> myDog2 = myComplexOperation(
                Mono.empty(),
                probe.mono())
                .log();

        StepVerifier.create(myDog2)
                .expectNextCount(1)
                .expectComplete()
                .verify();

        probe.assertWasSubscribed();
        probe.assertWasRequested();
        probe.assertWasNotCancelled();
    }

    private Mono<String> myComplexOperation(Mono<String> data, Mono<String> defaultValue) {
        return data.switchIfEmpty(defaultValue);
    }
}
