package es.codeurjc.arpj;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import static es.codeurjc.arpj.TestUtils.printSectionLine;
import static es.codeurjc.arpj.TestUtils.printTestLine;
import static org.assertj.core.api.Assertions.assertThat;

class FluxTest {

    private static final Faker FAKER = new Faker();

    @Test
    @DisplayName("Test 01: Basic Flux")
    void basic_flux_test() {

        System.out.println("Test 01: Basic Flux");
        printTestLine();

        final Flux<Integer> flux =
                Flux.just(1, 2, 3, 4, 5).log();

        StepVerifier
                .create(flux)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test 02: Flux from...")
    void flux_from_test() {

        System.out.println("Test 02: Flux from...");
        printTestLine();

        final String[]     strings    = {"one", "two", "three", "four", "five"};
        final Flux<String> stringFlux = Flux.fromArray(strings).log();

        StepVerifier
                .create(stringFlux)
                .expectNextCount(5)
                .expectComplete()
                .verify();

        printSectionLine();

        final List<String> cats    = List.of(FAKER.cat().name(), FAKER.cat().name(), FAKER.cat().name());
        final Flux<String> catFlux = Flux.fromIterable(cats).log();

        StepVerifier
                .create(catFlux)
                .expectNextCount(3)
                .expectComplete()
                .verify();

        printSectionLine();

        final Flux<Integer> integerFlux = Flux.range(1, 10).log();

        StepVerifier
                .create(integerFlux)
                .expectNextCount(10)
                .expectComplete()
                .verify();

        printSectionLine();

        final Stream<String> dragons = Stream.of(FAKER.gameOfThrones().dragon(), FAKER.gameOfThrones().dragon(),
                FAKER.gameOfThrones().dragon());
        final Flux<String> dragonFlux = Flux.fromStream(dragons).log();

        StepVerifier
                .create(dragonFlux)
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test 03: Flux map and cast")
    void flux_map_cast() {

        System.out.println("Test 03: Flux map and cast");
        printTestLine();

        final String[]      strings     = {"one", "two", "three", "four", "five"};
        final Flux<Integer> mapToLength = Flux.fromArray(strings).log().map(String::length).cast(Integer.class).log();

        StepVerifier
                .create(mapToLength)
                .expectNext(3)
                .expectNext(3)
                .expectNext(5)
                .expectNext(4)
                .expectNext(4)
                .expectComplete()
                .verify();

        printSectionLine();

        final Flux<Long> mapToIndex = Flux.fromArray(strings).log().index().map(Tuple2::getT1).log();

        StepVerifier
                .create(mapToIndex)
                .expectNext(0L, 1L, 2L, 3L, 4L)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test 04: Start and concat")
    void flux_start_concat() {

        System.out.println("Test 04: Start and concat");
        printTestLine();

        final String theFirst = FAKER.princessBride().character();
        System.out.println("My name is... " + theFirst + " and I want to be the first!!!");

        final Flux<String> fluxStarts = Flux.just(FAKER.starTrek().character(), FAKER.zelda().character())
                .startWith(theFirst)
                .delayElements(Duration.ofMillis(500))
                .doOnNext(System.out::println);

        StepVerifier
                .create(fluxStarts)
                .expectNext(theFirst)
                .expectNextCount(2)
                .expectComplete()
                .verify();

        printSectionLine();


        final Flux<String> fruit = Flux.just(FAKER.food().fruit(), FAKER.food().fruit());
        final Flux<String> dishes = Flux.just(FAKER.food().dish(), FAKER.food().dish())
                .concatWith(fruit)
                .delayElements(Duration.ofMillis(500))
                .doOnNext(System.out::println);

        StepVerifier
                .create(dishes)
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test 05: Aggregate. Boolean")
    void flux_aggregate() {

        System.out.println("Test 05: Aggregate. Boolean");
        printTestLine();

        final Mono<Boolean> allOddNumbers = Flux.just(1, 3, 5, 7, 9).all(i -> i % 2 != 0).log();

        StepVerifier
                .create(allOddNumbers)
                .expectNext(Boolean.TRUE)
                .expectComplete()
                .verify();

        printSectionLine();

        final Mono<Boolean> oneEvenNumber = Flux.just(1, 3, 5, 7, 9, 10).any(i -> i % 2 == 0).log();

        StepVerifier
                .create(oneEvenNumber)
                .expectNext(Boolean.TRUE)
                .expectComplete()
                .verify();

        printSectionLine();

        final String firstBook  = FAKER.book().title();
        final String secondBook = FAKER.book().title();
        final String thirdBook  = FAKER.book().title();

        final Mono<Boolean> hasOneItem = Flux.just(firstBook, secondBook, thirdBook).log().hasElement(secondBook);

        StepVerifier
                .create(hasOneItem)
                .expectNext(Boolean.TRUE)
                .expectComplete()
                .verify();

        printSectionLine();

        final String firstAuthor  = FAKER.book().author();
        final String secondAuthor = FAKER.book().author();
        final String thirdAuthor  = FAKER.book().author();

        final Mono<Boolean> hasItem    = Flux.just(firstAuthor, secondAuthor, thirdAuthor).log().hasElements();
        final Mono<Boolean> hasNoItems = Flux.empty().log().hasElements();

        StepVerifier
                .create(hasItem)
                .expectNext(Boolean.TRUE)
                .expectComplete()
                .verify();

        StepVerifier
                .create(hasNoItems)
                .expectNext(Boolean.FALSE)
                .expectComplete()
                .verify();

        // INFO: Check the logs to see the 'flow' of elements
    }

    @Test
    @DisplayName("Test 06: Aggregate. To list/map/container")
    void flux_aggregate_list_map() {

        System.out.println("Test 06: Aggregate. To list/map/container");
        printTestLine();

        final List<Integer> integers = Flux.just(1, 2, 3, 4, 5).collectList().block();
        integers.forEach(System.out::println);

        assertThat(integers).hasSize(5);

        printSectionLine();

        final List<String> strings = Flux
                .just(FAKER.country().name(), FAKER.country().name(), FAKER.country().name())
                .collectSortedList()
                .block();
        strings.forEach(System.out::println);

        assertThat(strings).hasSize(3);

        printSectionLine();

        final Map<String, String> gods = Flux
                .just(FAKER.ancient().god(), FAKER.ancient().god(), FAKER.ancient().god())
                .collectMap(g -> Instant.now().toString())
                .block();

        gods.keySet().forEach(System.out::println);
        gods.values().forEach(System.out::println);

        assertThat(gods).hasSize(3);

        printSectionLine();

        final Map<String, Collection<String>> airports = Flux.range(0, 20)
                .map(t -> FAKER.aviation().airport())
                .collectMultimap(
                        a -> String.valueOf(a.charAt(0)),
                        a -> a)
                .block();

        airports.forEach((key, value) -> System.out.println("The key is ... " + key
                + " and the values are... " + String.join(", ", value)));

        assertThat(airports).isNotEmpty();
    }

    @Test
    @DisplayName("Test 07: Aggregate. Reduce")
    void flux_aggregate_reduce() {

        System.out.println("Test 07: Aggregate. Reduce");
        printTestLine();

        final Long count = Flux.range(0, 15).count().block();
        System.out.println("The count is: " + count);

        printSectionLine();

        final Random random = new Random();

        final Integer sum = Flux
                .range(0, 15)
                .map(i -> i + random.nextInt(50))
                .reduce(Integer::sum)
                .block();
        System.out.println("The sum is: " + count);

        printSectionLine();

        final Integer starts = 0;

        final Flux<Integer> scan = Flux
                .range(0, 10)
                .map(i -> FAKER.lordOfTheRings().location())
                .doOnNext(System.out::println)
                .map(String::length)
                .scan(starts, Integer::sum)
                .doOnNext(System.out::println);

        StepVerifier
                .create(scan)
                .expectNextCount(11)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test 08: Transform")
    void flux_transform() {

        System.out.println("Test 08: Transform");
        printTestLine();

        final Scheduler myParallel = Schedulers.newParallel("my-parallel", 2);

        final Function<Flux<String>, Flux<Integer>> myTransformation =
                flux -> flux.publishOn(myParallel).map(String::length);

        final String[]      strings     = {"one", "two", "three", "four", "five"};
        final Flux<Integer> mapToLength = Flux.fromArray(strings).transform(myTransformation).log();

        StepVerifier
                .create(mapToLength)
                .expectNextCount(5)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test 09: Concat map")
    void flux_concat_map() {

        System.out.println("Test 09: Concat map");
        printTestLine();

        final Scheduler myParallel = Schedulers.newParallel("my-parallel", 4);

        final Flux<Integer> numbers = Flux.range(0, 10)
                .window(2)
                .concatMap(g -> g.flatMap(this::processing).subscribeOn(myParallel))
                .log();

        StepVerifier
                .create(numbers)
                .expectNextCount(10)
                .expectComplete()
                .verify();

        printTestLine();

        final Flux<Integer> numbers2 = Flux.range(0, 10)
                .window(2)
                .flatMap(g -> g.flatMap(this::processing).subscribeOn(myParallel))
                .log();

        StepVerifier
                .create(numbers2)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    private Mono<Integer> processing(final Integer input) {

        final var random = new Random();

        final var millis = random.nextInt(10) * 100L;

        System.out.println("Sleeping [" + input + "] " + millis + " milliseconds... @ "
                + Thread.currentThread().getName());

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Mono.just(input);
    }

    @Test
    @DisplayName("Test 10: Handle")
    void flux_handle() {

        System.out.println("Test 10: Handle");
        printTestLine();

        final Flux<Integer> numbers = Flux.range(0, 10)
                .handle((n, s) -> {
                    if (n % 2 == 0) {
                        System.out.println("Even!!! -> " + n);
                        s.next(n);
                    } else {
                        s.next(0);
                    }
                }).cast(Integer.class).log();

        StepVerifier
                .create(numbers)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }
}
