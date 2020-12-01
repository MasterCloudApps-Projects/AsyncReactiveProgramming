package es.codeurjc.arpj;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import static es.codeurjc.arpj.TestUtils.printTestLine;

class DebugTest {

    private static final Faker FAKER = new Faker();

    @Test
    @DisplayName("Test 01: Hooks.onOperatorDebug()")
    void debug_hooks() {

        System.out.println("Test 01: Hooks.onOperatorDebug()");
        printTestLine();

        // Hooks.onOperatorDebug();

        Flux.just(1, 2, 3, null, 5, 6, 7, null, 8, 9, 10)
                .map(x -> "This is the number: " + x)
                .doOnNext(System.out::println).subscribe();
    }

    @Test
    @DisplayName("Test 02: Checkpoint")
    void debug_checkpoint() {

        System.out.println("Test 02: Checkpoint");
        printTestLine();

        Flux.just(1, 2, 3, 4, 5, 6)
                .map(x -> 2 / 0)
                .checkpoint()
                .doOnNext(System.out::println)
                .subscribe();

        Flux.just(1, 2, 3, 4, 5, 6)
                .map(x -> x + 1)
                .checkpoint("The-First")
                .doOnNext(System.out::println)
                .map(x -> x / 0)
                .checkpoint("The-Second")
                .doOnNext(System.out::println)
                .subscribe();
    }

    @Test
    @DisplayName("Test 03: Log")
    void debug_log() {

        System.out.println("Test 03: Log");
        printTestLine();

        Flux.range(0, 10)
                .map(x -> FAKER.superhero().name())
                .log()
                .subscribe();
    }
}
