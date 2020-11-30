package es.codeurjc.arpj;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import static es.codeurjc.arpj.TestUtils.printTestLine;

class TestPublisherTest {

    private static final Faker FAKER = new Faker();

    @Test
    @DisplayName("Test 01: Test Publisher. Basic")
    void basic_test_publisher_1() {

        System.out.println("Test 01: Test Publisher. Basic");
        printTestLine();

        final TestPublisher<String> myTestPublisher =
                TestPublisher.create();

        final GreeterService greeterService =
                new GreeterService(myTestPublisher.flux());

        StepVerifier
                .create(greeterService.greet())
                .then(() -> myTestPublisher.emit(
                        FAKER.cat().name(),
                        FAKER.cat().name(),
                        FAKER.cat().name()
                ))
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 02: Test Publisher. Basic")
    void basic_test_publisher_2() {

        System.out.println("Test 01: Test Publisher. Basic");
        printTestLine();

        final TestPublisher<String> myTestPublisher =
                TestPublisher.create();

        final GreeterService greeterService =
                new GreeterService(myTestPublisher.flux());

        StepVerifier
                .create(greeterService.greet())
                .then(() -> myTestPublisher.next(
                        FAKER.dog().name()
                ))
                .expectNextCount(1)
                .then(() -> myTestPublisher
                        .error(new IllegalAccessException()))
                .verifyError();
    }

    @Test
    @DisplayName("Test 03: Test Publisher. Basic")
    void basic_test_publisher_3() {

        System.out.println("Test 03: Test Publisher. Basic");
        printTestLine();

        final TestPublisher<String> myTestPublisher =
                TestPublisher.create();

        Flux<String> myFlux = Flux.from(myTestPublisher)
                .map(x -> "Hello, " + x + "!!!")
                .doOnNext(System.out::println)
                .doOnError(x -> System.out.println("Goodbye!!!"));

        StepVerifier
                .create(myFlux)
                .then(() -> myTestPublisher.next(
                        FAKER.lebowski().actor(),
                        FAKER.lebowski().actor()
                ))
                .expectNextCount(2)
                .then(() -> myTestPublisher
                        .error(new Exception()))
                .expectError()
                .verify();
    }

    @Test
    @DisplayName("Test 04: Test Publisher. No compilant")
    void basic_test_publisher_4() {

        System.out.println("Test 04: Test Publisher. No compilant");
        printTestLine();

        final TestPublisher<String> myTestPublisher =
                TestPublisher.createNoncompliant(TestPublisher.Violation.ALLOW_NULL);

        Flux<String> myFlux = Flux.from(myTestPublisher)
                .map(x -> "Hello, " + x + "!!!")
                .doOnNext(System.out::println)
                .doOnError(x -> System.out.println("Goodbye!!!"));

        StepVerifier
                .create(myFlux)
                .then(() -> myTestPublisher.emit(
                        FAKER.lebowski().actor(),
                        null
                ))
                .expectNextCount(2)
                .verifyComplete();
    }
}

class GreeterService {
    private final Flux<String> source;

    GreeterService(Flux<String> source) {
        this.source = source;
    }

    Flux<String> greet() {
        return source.map(x -> "Hello, " + x + "!!!").doOnNext(System.out::println);
    }
}

