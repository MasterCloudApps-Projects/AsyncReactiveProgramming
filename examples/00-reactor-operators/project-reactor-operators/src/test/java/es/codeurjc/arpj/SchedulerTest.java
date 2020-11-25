package es.codeurjc.arpj;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
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

        printSectionLine();

        final Mono<String> myMono1 = Mono.just(FAKER.company().name())
                .delayElement(Duration.ofSeconds(random.nextInt(5)))
                .doOnNext(e -> System.out.println(e + " - " + Thread.currentThread().getName()));

        final Mono<String> myMono2 = Mono.just(FAKER.company().name())
                .delayElement(Duration.ofSeconds(random.nextInt(5)))
                .doOnNext(e -> System.out.println(e + " - " + Thread.currentThread().getName()));

        final Mono<String> myMono3 = Mono.just(FAKER.company().name())
                .delayElement(Duration.ofSeconds(random.nextInt(5)))
                .doOnNext(e -> System.out.println(e + " - " + Thread.currentThread().getName()));

        final Mono<String> myMono4 = Mono.just(FAKER.company().name())
                .delayElement(Duration.ofSeconds(random.nextInt(5)))
                .doOnNext(e -> System.out.println(e + " - " + Thread.currentThread().getName()));

        final Mono<String> myZipMono = Mono.zip(myMono1, myMono2, myMono3, myMono4)
                .map(z -> "Companies: " + z.getT1() + " / " + z.getT2() + " / " + z.getT3() + " / " + z.getT4())
                .doOnNext(System.out::println);

        StepVerifier
                .create(myZipMono)
                .expectNextCount(1)
                .expectComplete()
                .verify();

        printSectionLine();

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

    @Test
    @DisplayName("Test 02: Bounded elastic. Basic")
    void bounded_elastic_scheduler_test_1() {

        System.out.println("Test 02: Bounded elastic. Basic");
        printTestLine();

        final Scheduler myBoundedElastic = Schedulers.newBoundedElastic(5, 5, "my-bounded-elastic");

        final ParallelFlux<Integer> myFlux = Flux.range(0, 3)
                .parallel()
                .runOn(myBoundedElastic)
                .doOnNext(e -> System.out.println(e + " - " + Thread.currentThread().getName()));

        StepVerifier
                .create(myFlux)
                .expectNextCount(3)
                .expectComplete()
                .verify();

        printSectionLine();

        final ParallelFlux<Integer> myFlux2 = Flux.range(0, 7)
                .parallel()
                .runOn(myBoundedElastic)
                .doOnNext(e -> System.out.println(e + " - " + Thread.currentThread().getName()));

        StepVerifier
                .create(myFlux2)
                .expectNextCount(7)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test 03: Bounded elastic. TTL expiration")
    void bounded_elastic_scheduler_test_3() throws InterruptedException {

        final Random random = new Random();

        System.out.println("Test 03: Bounded elastic. TTL expiration");
        printTestLine();

        System.out.println("""
                In this section we create a new bounded elastic scheduler with a short TTL.
                                
                After the execution of myFlux4, we force the scheduler TTL expiration through a Thread.sleep()
                """);

        final Scheduler myBoundedElastic3 = Schedulers.newBoundedElastic(5, 5, "my-bounded-elastic-III", 3);

        final ParallelFlux<Integer> myFlux4 =
                Flux.range(0, 5)
                        .parallel()
                        .runOn(myBoundedElastic3)
                        .doOnNext(e -> {
                            try {
                                Thread.sleep(random.nextInt(10) * 50);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            System.out.println(e + " - " + Thread.currentThread().getName());
                        });

        StepVerifier
                .create(myFlux4)
                .expectNextCount(5)
                .expectComplete()
                .verify();

        System.out.println("\nWaiting...\n");
        Thread.sleep(6000);

        final ParallelFlux<Integer> myFlux5 =
                Flux.range(0, 5)
                        .parallel()
                        .runOn(myBoundedElastic3)
                        .doOnNext(e -> {
                            try {
                                Thread.sleep(random.nextInt(10) * 50);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            System.out.println(e + " - " + Thread.currentThread().getName());
                        });

        StepVerifier
                .create(myFlux5)
                .expectNextCount(5)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test 04: Immediate")
    void immediate_test() {

        System.out.println("Test 04: Immediate");
        printTestLine();

        final Flux<Integer> myFlux = Flux.range(0, 10)
                .publishOn(Schedulers.immediate())
                .doOnNext(i -> System.out.println(i + " - " + Thread.currentThread().getName()));

        StepVerifier
                .create(myFlux)
                .expectNextCount(10)
                .verifyComplete();

        printSectionLine();

        final Flux<Integer> myFlux2 = Flux.range(0, 10)
                .publishOn(Schedulers.newParallel("my-parallel"))
                .doOnNext(i -> System.out.println(i + " - " + Thread.currentThread().getName()))
                .publishOn(Schedulers.newParallel("my-second-parallel"))
                .publishOn(Schedulers.immediate())
                .doOnNext(i -> System.out.println(i + " - " + Thread.currentThread().getName()));

        StepVerifier
                .create(myFlux2)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 05: Single")
    void single_test() throws InterruptedException {

        System.out.println("Test 05: Single");
        printTestLine();

        final Flux<Integer> myFlux = Flux.range(0, 10)
                .publishOn(Schedulers.newSingle("my-single"))
                .doOnNext(i -> System.out.println(i + " - " + Thread.currentThread().getName()));

        StepVerifier
                .create(myFlux)
                .expectNextCount(10)
                .verifyComplete();

        printSectionLine();

        Flux.range(0, 10)
                .publishOn(Schedulers.newSingle("my-second-single"))
                .doOnNext(i -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(i + " - " + Thread.currentThread().getName());
                }).subscribe();


        Thread.sleep(2500);
        System.out.println("Hey! - " + Thread.currentThread().getName());
        Thread.sleep(5000);
    }
}
