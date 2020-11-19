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
class CharacterTest {

    private static final String INT_TESTS_CHARACTERS = "### --> Integration Test: Characters. {} ";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    @DisplayName("Get a random character")
    void random_character() {

        log.info(INT_TESTS_CHARACTERS, "[Get a random character]");
        System.out.println("----------------------------------------------------------");
        get("/characters/random")
                .then().assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", notNullValue())
                .body("status", notNullValue())
                .body("species", notNullValue())
                .body("image", notNullValue())
                .log().body();
        System.out.println("----------------------------------------------------------");
    }
}
