package es.codeurjc.arpj;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;

class MonoTest {

    private static final Faker FAKER = new Faker();

    @Test
    @DisplayName("Test 01: Basic Mono")
    void basic_mono_test() {

        System.out.println("Test 01: Basic Mono");
        System.out.println("=======================================================================================\n");

        final String myCharacter = FAKER.backToTheFuture().character();
        final Mono<String> myMono = Mono.just(myCharacter).log();

        StepVerifier
                .create(myMono)
                .expectNext(myCharacter)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test 02: Just or Empty")
    void mono_just_or_empty_test() {

        System.out.println("Test 02: Just or Empty");
        System.out.println("=======================================================================================\n");

        final String myCharacter = FAKER.gameOfThrones().character();
        final Mono<String> myMono = Mono.justOrEmpty(myCharacter).log();

        StepVerifier
                .create(myMono)
                .expectNext(myCharacter)
                .expectComplete()
                .verify();

        final Mono<Object> myEmptyMono = Mono.justOrEmpty(Optional.empty()).log();

        StepVerifier
                .create(myEmptyMono)
                .expectComplete()
                .verify();

        final Mono<Object> myNullMono = Mono.justOrEmpty(null).log();

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

    @Test
    @DisplayName("Test 03: Never")
    void mono_never() {

        System.out.println("Test 03: Never");
        System.out.println("=======================================================================================\n");

        final Mono<String> neverMono = Mono.never();

        StepVerifier
                .create(neverMono)
                .expectTimeout(Duration.ofSeconds(2)).verify();
    }

    @Test
    @DisplayName("Test 04: Error")
    void mono_error() {

        System.out.println("Test 04: Error");
        System.out.println("=======================================================================================\n");

        final Mono<Object> errorMono = Mono.error(new Exception()).log();

        StepVerifier
                .create(errorMono)
                .expectError()
                .verify();

        final Mono<Object> errorSupplierMono = Mono.error(IndexOutOfBoundsException::new).log();

        StepVerifier
                .create(errorSupplierMono)
                .expectError()
                .verify();
    }

    @Test
    @DisplayName("Test 05: Zip with")
    void mono_zip_with() {

        System.out.println("Test 05: Zip with");
        System.out.println("=======================================================================================\n");

        final Mono<String> firstMono = Mono.just(FAKER.harryPotter().character());
        final Mono<String> secondMono = Mono.just(FAKER.harryPotter().house());

        final Mono<String> zippedMono =
                firstMono.zipWith(secondMono).map(t -> t.getT1() + " goes to... " + t.getT2() + "!!!").log();

        StepVerifier
                .create(zippedMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 06: Zip")
    void mono_zip() {

        System.out.println("Test 06: Zip");
        System.out.println("=======================================================================================\n");

        final Mono<String> firstCity = Mono.just(FAKER.country().capital());
        final Mono<String> secondCity = Mono.just(FAKER.country().capital()).delayElement(Duration.ofMillis(2500));
        final Mono<String> thirdCity = Mono.just(FAKER.country().capital()).delayElement(Duration.ofMillis(750));

        final Mono<String> zippedMono = Mono.zip(firstCity, secondCity, thirdCity).map(t -> "I want to visit: "
                + t.getT1() + ", " + t.getT2() + ", " + t.getT3()).log();

        StepVerifier
                .create(zippedMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 07: Repeat")
    void mono_repeat() {

        System.out.println("Test 07: Repeat");
        System.out.println("=======================================================================================\n");

        final Flux<String> artist =
                Mono.just(FAKER.artist().name()).delayElement(Duration.ofSeconds(1)).repeat(3).log();

        StepVerifier
                .create(artist)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 08: First and or")
    void mono_first_and() {

        final Random random = new Random();

        System.out.println("Test 08: First and or");
        System.out.println("=======================================================================================\n");

        final Mono<String> firstAnimal = Mono.just(FAKER.animal().name()).log()
                .delayElement(Duration.ofMillis(100 * random.nextInt(10)));
        final Mono<String> secondAnimal = Mono.just(FAKER.animal().name()).log()
                .delayElement(Duration.ofMillis(100 * random.nextInt(10)));
        final Mono<String> thirdAnimal = Mono.just(FAKER.animal().name()).log()
                .delayElement(Duration.ofMillis(100 * random.nextInt(10)));

        final Mono<String> firstRace = Mono.firstWithSignal(firstAnimal, secondAnimal, thirdAnimal)
                .map(a -> a.toUpperCase() + " is the winner!!!").log();

        final Mono<String> secondRace = firstAnimal.or(secondAnimal).or(thirdAnimal)
                .map(a -> a.toUpperCase() + " is the winner!!!").log();

        StepVerifier
                .create(firstRace)
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier
                .create(secondRace)
                .expectNextCount(1)
                .verifyComplete();
    }
}
