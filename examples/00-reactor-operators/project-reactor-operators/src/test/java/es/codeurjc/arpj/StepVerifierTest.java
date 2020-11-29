package es.codeurjc.arpj;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

import java.time.Duration;
import java.time.Instant;

import static es.codeurjc.arpj.TestUtils.printTestLine;
import static org.assertj.core.api.Assertions.assertThat;

class StepVerifierTest {

    private static final Faker FAKER = new Faker();

    @Test
    @DisplayName("Test 01: Step verifier. Basic")
    void basic_step_verifier_1() {

        System.out.println("Test 01: Step verifier. Basic");
        printTestLine();

        final String myPokemon =
                FAKER.pokemon().name();

        final Mono<String> myMono =
                Mono.just(myPokemon).log();

        StepVerifier
                .create(myMono)
                .expectNext(myPokemon)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test 02: Step verifier. Basic")
    void basic_step_verifier_2() {

        System.out.println("est 02: Step verifier. Basic");
        printTestLine();

        final Flux<Integer> flux =
                Flux.just(1, 2, 3, 4, 5).log();

        StepVerifier
                .create(flux)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 03: Consume next with")
    void step_verifier_consume_next_with() {

        System.out.println("Test 03: Consume next with");
        printTestLine();

        final Flux<String> flux =
                Flux.just("A", "b", "c", "d", "e").log();

        StepVerifier
                .create(flux)
                .consumeNextWith(x -> assertThat(x).isUpperCase())
                .assertNext(x -> assertThat(x).isLowerCase())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 04: Then / Then await")
    void step_verifier_then_then_await() {

        // VirtualTimeScheduler.getOrSet();

        System.out.println("Test 04: Then / Then await");
        printTestLine();

        // INFO: Don't do this
        // final Flux<Long> flux = Flux.interval(Duration.ofDays(1)).take(2);

        StepVerifier
                .withVirtualTime(() ->
                        Flux.interval(Duration.ofDays(1)).take(2))
                .expectSubscription()
                .thenAwait(Duration.ofDays(1))
                .expectNext(0L)
                .thenAwait(Duration.ofDays(1))
                .expectNext(1L)
                .then(() -> System.out.println("Hello @ " + Instant.now() + "!!!"))
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 05: Context 1")
    void step_verifier_context_1() {

        System.out.println("Test 05: Context 1");
        printTestLine();

        StepVerifier.create(

                Flux.range(0, 5).map(i -> i + 10),

                StepVerifierOptions
                        .create()
                        .withInitialContext(Context.of(
                                "my-context-key-1", "content-1",
                                "my-context-key-2", "content-2")
                        ))

                .expectAccessibleContext()
                .contains("my-context-key-1", "content-1")
                .contains("my-context-key-2", "content-2")
                .then()
                .expectNext(10)
                .expectNextCount(4)
                .then(() -> System.out.println("The context is propagated!!! :-)"))
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 05: Context 2")
    void step_verifier_context_2() {

        System.out.println("Test 05: Context 2");
        printTestLine();

        StepVerifier.create(

                Flux.range(0, 5)
                        .contextWrite(ctx -> ctx.put("my-key", "my-value"))
                        .map(i -> i + 10),

                StepVerifierOptions
                        .create())

                .expectAccessibleContext()
                .contains("my-key", "my-value")
                .then()
                .expectNext(10)
                .expectNextCount(4)
                .then(() -> System.out.println("The context is propagated!!! :-)"))
                .verifyComplete();
    }
}