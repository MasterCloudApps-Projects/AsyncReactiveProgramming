package es.codeurjc.arpj.r2dbc;

import es.codeurjc.arpj.r2dbc.infrastructure.http.AstronautHttpRequest;
import es.codeurjc.arpj.r2dbc.infrastructure.http.AstronautHttpResponse;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AstronautIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    final String INT_TESTS_ASTRONAUTS = "### --> Integration Test: Astronauts. {} ";

    @LocalServerPort
    private int port;

    @BeforeAll
    void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    @DisplayName("Astronaut flow: random")
    void random_astronaut() {

        final AstronautHttpResponse response = getRandomAstronaut();
        log.info(response.toString());

        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("Astronaut flow: create, get all, get by id, update, and delete")
    void create_client_flow() throws JsonProcessingException {

        log.info(INT_TESTS_ASTRONAUTS, "Get all astronauts...");
        final var allAstronauts = getAllAstronauts();

        assertThat(allAstronauts).isNotEmpty();

        log.info(INT_TESTS_ASTRONAUTS, "Save astronaut...");
        final var newAstronaut = saveAstronaut(AstronautHttpRequestMother.random());
        log.info(newAstronaut.toString());

        assertThat(newAstronaut).isNotNull();

        log.info(INT_TESTS_ASTRONAUTS, "Get astronaut by id...");
        final var astronautById = getAstronautById(String.valueOf(newAstronaut.getId()));
        log.info(astronautById.toString());

        assertThat(astronautById).isNotNull();

        log.info(INT_TESTS_ASTRONAUTS, "Update astronaut...");
        updateAstronaut(AstronautHttpRequestMother.random(astronautById.getId()));
        log.info(INT_TESTS_ASTRONAUTS, "Get astronaut by id...");
        final var updatedAstronautById = getAstronautById(String.valueOf(newAstronaut.getId()));
        log.info(updatedAstronautById.toString());

        assertThat(updatedAstronautById).isNotNull();

        log.info(INT_TESTS_ASTRONAUTS, "Delete astronaut...");
        deleteAstronautById(String.valueOf(updatedAstronautById.getId()));
        notFoundAstronautById(String.valueOf(updatedAstronautById.getId()));
    }

    private List<AstronautHttpResponse> getAllAstronauts() {
        return Arrays.asList(get("/astronauts")
                .then().statusCode(200)
                .and().extract().body().as(AstronautHttpResponse[].class));
    }

    private AstronautHttpResponse getAstronautById(final String astronautId) {
        return get("/astronauts/" + astronautId)
                .then().statusCode(200)
                .and().extract().body().as(AstronautHttpResponse.class);
    }

    private void notFoundAstronautById(final String astronautId) {
        get("/astronauts/" + astronautId).then().statusCode(404);
    }

    private AstronautHttpResponse getRandomAstronaut() {
        return get("/astronauts/random")
                .then().statusCode(200)
                .and().extract().body().as(AstronautHttpResponse.class);
    }

    private AstronautHttpResponse saveAstronaut(final AstronautHttpRequest astronaut) throws JsonProcessingException {
        return with().body(objectMapper.writeValueAsString(astronaut))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .request("POST", "/astronauts")
                .then().statusCode(201)
                .and().extract().body().as(AstronautHttpResponse.class);
    }

    private void updateAstronaut(final AstronautHttpRequest astronaut) throws JsonProcessingException {
        with().body(objectMapper.writeValueAsString(astronaut))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .request("PUT", "/astronauts")
                .then()
                .statusCode(200);
    }

    private void deleteAstronautById(final String astronautId) {
        delete("/astronauts/" + astronautId).then().statusCode(204);
    }

}
