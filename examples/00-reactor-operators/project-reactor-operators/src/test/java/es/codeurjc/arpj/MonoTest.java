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

import static es.codeurjc.arpj.TestUtils.printSectionLine;
import static es.codeurjc.arpj.TestUtils.printTestLine;
import static org.assertj.core.api.Assertions.assertThat;

class MonoTest {

    private static final Faker FAKER = new Faker();

    @Test
    @DisplayName("Test 01: Basic Mono")
    void basic_mono_test() {

        System.out.println("Test 01: Basic Mono");
        printTestLine();

        final String       myCharacter = FAKER.backToTheFuture().character();
        final Mono<String> myMono      = Mono.just(myCharacter).log();

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
        printTestLine();

        final String       myCharacter = FAKER.gameOfThrones().character();
        final Mono<String> myMono      = Mono.justOrEmpty(myCharacter).log();

        StepVerifier
                .create(myMono)
                .expectNext(myCharacter)
                .expectComplete()
                .verify();

        printSectionLine();

        final Mono<Object> myEmptyMono = Mono.justOrEmpty(Optional.empty()).log();

        StepVerifier
                .create(myEmptyMono)
                .expectComplete()
                .verify();

        printSectionLine();

        final Mono<Object> myNullMono = Mono.justOrEmpty(null).log();

        StepVerifier
                .create(myNullMono)
                .expectComplete()
                .verify();

        /*
        TestUtils.printSectionLine();

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
        printTestLine();

        final Mono<String> neverMono = Mono.never();

        StepVerifier
                .create(neverMono)
                .expectTimeout(Duration.ofSeconds(2))
                .verify();
    }

    @Test
    @DisplayName("Test 04: Mono Error")
    void mono_error() {

        System.out.println("Test 04: Mono Error");
        printTestLine();

        final Mono<Object> errorMono = Mono.error(new Exception()).log();

        StepVerifier
                .create(errorMono)
                .expectError()
                .verify();

        printSectionLine();

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
        printTestLine();

        final Mono<String> firstMono  = Mono.just(FAKER.harryPotter().character());
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
        printTestLine();

        final Mono<String> firstCity  = Mono.just(FAKER.country().capital()).log();
        final Mono<String> secondCity = Mono.just(FAKER.country().capital()).delayElement(Duration.ofMillis(750)).log();
        final Mono<String> thirdCity  = Mono.just(FAKER.country().capital()).delayElement(Duration.ofMillis(250)).log();

        final Mono<String> zippedMono = Mono.zip(firstCity, secondCity, thirdCity)
                .map(t -> "I want to visit: " + t.getT1() + ", " + t.getT2() + ", " + t.getT3())
                .doOnNext(System.out::println);

        StepVerifier
                .create(zippedMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 07: Repeat")
    void mono_repeat() {

        System.out.println("Test 07: Repeat");
        printTestLine();

        final Flux<String> artist =
                Mono.just(FAKER.artist().name()).delayElement(Duration.ofSeconds(1)).repeat(3).log();

        StepVerifier
                .create(artist)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 08: First and or")
    void mono_first_and_or() {

        final Random random = new Random();

        System.out.println("Test 08: First and or");
        printTestLine();

        final Mono<String> firstAnimal = Mono.just(FAKER.animal().name()).log()
                .delayElement(Duration.ofMillis(100 * random.nextInt(10)));
        final Mono<String> secondAnimal = Mono.just(FAKER.animal().name()).log()
                .delayElement(Duration.ofMillis(100 * random.nextInt(10)));
        final Mono<String> thirdAnimal = Mono.just(FAKER.animal().name()).log()
                .delayElement(Duration.ofMillis(100 * random.nextInt(10)));

        final Mono<String> firstRace = Mono.firstWithSignal(firstAnimal, secondAnimal, thirdAnimal)
                .map(a -> a.toUpperCase() + " is the winner!!!").doOnNext(System.out::println);

        StepVerifier
                .create(firstRace)
                .expectNextCount(1)
                .verifyComplete();

        printSectionLine();

        final Mono<String> secondRace = firstAnimal.or(secondAnimal).or(thirdAnimal)
                .map(a -> a.toUpperCase() + " is the winner!!!").doOnNext(System.out::println);

        StepVerifier
                .create(secondRace)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 09: Then")
    void mono_then() {

        final Random random = new Random();

        System.out.println("Test 09: Then");
        printTestLine();

        final Mono<Void> character = Mono.just(FAKER.dragonBall().character()).log()
                .delayElement(Duration.ofSeconds(2))
                .then().doFinally(c -> System.out.println("Kamehamehaaaa!!!"));

        StepVerifier
                .create(character)
                .expectComplete()
                .verify();

        printSectionLine();

        final Mono<String> team = Mono.just(FAKER.dragonBall().character())
                .delayElement(Duration.ofSeconds(1))
                .doOnNext(c1 -> System.out.println(c1 + " has died!!! We need another hero!!!"))
                .then(Mono.just(FAKER.dragonBall().character()))
                .delayElement(Duration.ofSeconds(1))
                .doOnNext(c2 -> System.out.println(c2 + " has come!!! We are saved!!!"));

        StepVerifier
                .create(team)
                .expectNextCount(1)
                .verifyComplete();

        printSectionLine();

        final Mono<String> power = Mono.just(FAKER.dragonBall().character())
                .delayElement(Duration.ofSeconds(2))
                .doOnNext(c1 -> System.out.println("Hi! I'm ... " + c1))
                .thenReturn("Oh my god! It's over " + random.nextInt(10) * 1000 + "!!!")
                .doOnNext(System.out::println);

        StepVerifier
                .create(power)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 10: On Error")
    void mono_on_error() {

        System.out.println("Test 10: On Error");
        printTestLine();

        final Mono<Object> expertise = Mono.just(FAKER.programmingLanguage().name()).log()
                .delayElement(Duration.ofSeconds(1))
                .doOnNext(p1 -> System.out.println("We are " + p1 + " experts!!"))
                .then(Mono.error(Exception::new))
                .onErrorReturn(FAKER.programmingLanguage().name())
                .doOnNext(p2 -> System.out.println("But the ACTUAL experience... " + p2));

        StepVerifier
                .create(expertise)
                .expectNextCount(1)
                .verifyComplete();

        printSectionLine();

        final Mono<Object> monolith = Mono.just(FAKER.programmingLanguage().name()).log()
                .delayElement(Duration.ofSeconds(1))
                .doOnNext(p1 -> System.out.println("Everything is fine with our monolith written in... " + p1 + "!!!"))
                .then(Mono.error(Exception::new))
                .onErrorResume(e -> Mono.just(FAKER.programmingLanguage().name()))
                .doOnNext(p2 ->
                        System.out.println("Oh no!!! An error!!! Let's rewrite the entire codebase in... " + p2));

        StepVerifier
                .create(monolith)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 11: Block")
    void mono_block() {

        System.out.println("Test 11: Block");
        printTestLine();

        final String medicine = Mono.just(FAKER.medical().medicineName()).log()
                .doOnNext(m -> System.out.println("Starting an expensive operation. Synthesizing..." + m))
                .delayElement(Duration.ofSeconds(5))
                .doOnNext(e -> System.out.println("The operation is complete!!!"))
                .block();

        assertThat(medicine).isNotBlank();
    }
}
