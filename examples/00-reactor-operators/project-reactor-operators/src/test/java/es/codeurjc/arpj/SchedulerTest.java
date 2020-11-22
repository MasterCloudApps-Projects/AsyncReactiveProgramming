package es.codeurjc.arpj;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Random;

import static es.codeurjc.arpj.TestUtils.printSectionLine;
import static es.codeurjc.arpj.TestUtils.printTestLine;

class SchedulerTest {

    private static final Faker FAKER = new Faker();

    @Test
    @DisplayName("Test 01: Parallel")
    void parallel_scheduler_test() {

        final Random random = new Random();

        System.out.println("Test 01: Parallel");
        printTestLine();

        final Flux<Integer> myBasicFlux = Flux.range(0, 10)
                .doOnNext(e -> System.out.println(e + " - " + Thread.currentThread().getName()));

        StepVerifier
                .create(myBasicFlux)
                .expectNextCount(10)
                .expectComplete()
                .verify();

        /* [...] */

        final Scheduler myScheduler = Schedulers.newParallel("my-own-scheduler", 4);

        final Flux<String> myFlux = Flux.range(0, 5)
                .doOnNext(e -> System.out.println(e + " - " + Thread.currentThread().getName()))
                .map(i -> FAKER.friends().character())
                .publishOn(myScheduler)
                .doOnNext(e -> System.out.println(e + " - " + Thread.currentThread().getName()))
                .delayElements(Duration.ofSeconds(random.nextInt(2)))
                .doOnNext(e -> System.out.println(e + " - " + Thread.currentThread().getName()));

        StepVerifier
                .create(myFlux)
                .expectNextCount(5)
                .expectComplete()
                .verify();
    }
}
