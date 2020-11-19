package es.codeurjc.arpj.webclient;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnimalTest {

    private static final String INT_TESTS_CHARACTERS = "### --> Integration Test: Animals. {} ";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    @DisplayName("Get a random cat")
    void random_cat() {

        log.info(INT_TESTS_CHARACTERS, "[Get a random cat]");
        long start = System.currentTimeMillis();

        get("/cats/random")
                .then().assertThat()
                .statusCode(200)
                .body("cat", notNullValue())
                .log().body();

        log.info("Elapsed time: {} ms.", System.currentTimeMillis() - start);
    }

    @Test
    @DisplayName("Get a random dog")
    void random_dog() {

        log.info(INT_TESTS_CHARACTERS, "[Get a random dog]");
        long start = System.currentTimeMillis();

        get("/dogs/random")
                .then().assertThat()
                .statusCode(200)
                .body("dog", notNullValue())
                .log().body();

        log.info("Elapsed time: {} ms.", System.currentTimeMillis() - start);
    }

    @Test
    @DisplayName("Get a random fox")
    void random_fox() {

        log.info(INT_TESTS_CHARACTERS, "[Get a random fox]");
        long start = System.currentTimeMillis();

        get("/foxes/random")
                .then().assertThat()
                .statusCode(200)
                .body("fox", notNullValue())
                .log().body();

        log.info("Elapsed time: {} ms.", System.currentTimeMillis() - start);
    }

    @Test
    @DisplayName("Get a random animal group")
    void random_animal_group() {

        log.info(INT_TESTS_CHARACTERS, "[Get a random animal group]");
        long start = System.currentTimeMillis();

        get("/animals/random")
                .then().assertThat()
                .statusCode(200)
                .body("cat", notNullValue())
                .body("dog", notNullValue())
                .body("fox", notNullValue())
                .log().body();

        log.info("Elapsed time: {} ms.", System.currentTimeMillis() - start);
    }
}
