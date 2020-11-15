package es.codeurjc.arpj.mvcapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.codeurjc.arpj.mvcapi.infrastructure.http.UserRequest;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserTest {

    private static final String INT_TESTS_USERS = "### --> Integration Test: Users. {} ";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    @DisplayName("User flow: create, get all, get by id, update and delete")
    void user_flow() throws JsonProcessingException {

        // Arrange

        final UserRequest request = UserRequestMother.random();
        log.info(INT_TESTS_USERS, "[Create Request]");

        final UserRequest modifiedRequest = UserRequestMother.random(request.getId());
        log.info(INT_TESTS_USERS, "[Create Modified Request]");

        // Act & Assert

        postUser(request);
        log.info(INT_TESTS_USERS, "[Post User]");

        getAllUsers(1);
        log.info(INT_TESTS_USERS, "[Get All Users]");

        getUserById(request);
        log.info(INT_TESTS_USERS, "[Get User By Id]");

        putUser(modifiedRequest);
        log.info(INT_TESTS_USERS, "[Put User (modify)]");

        getUserById(modifiedRequest);
        log.info(INT_TESTS_USERS, "[Get User By Id (modified)]");

        deleteUser(request.getId());
        log.info(INT_TESTS_USERS, "[Delete User]");

        getUserNotFound(request);
        log.info(INT_TESTS_USERS, "[Get User By Id (not found)]");

        getAllUsers(0);
        log.info(INT_TESTS_USERS, "[Get All Users (zero)]");
    }

    private void postUser(final UserRequest request) throws JsonProcessingException {
        with().body(MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .request("POST", "/users")
                .then().assertThat()
                .statusCode(201);
    }

    private void getAllUsers(final int expectedCount) {
        get("/users")
                .then().assertThat()
                .statusCode(200)
                .body("size()", is(expectedCount));
    }

    private void getUserById(final UserRequest request) {
        System.out.println("----------------------------------------------------------");
        get("/users/" + request.getId())
                .then().assertThat()
                .statusCode(200)
                .body("id", equalTo(request.getId()))
                .body("name", equalTo(request.getName()))
                .body("surname", equalTo(request.getSurname()))
                .body("email", equalTo(request.getEmail())).log().body();
        System.out.println("----------------------------------------------------------");
    }

    private void getUserNotFound(final UserRequest request) {
        get("/users/" + request.getId())
                .then().assertThat()
                .statusCode(404);
    }

    private void putUser(final UserRequest modifiedUserRequest) throws JsonProcessingException {
        with().body(MAPPER.writeValueAsString(modifiedUserRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .request("PUT", "/users")
                .then().assertThat()
                .statusCode(200);
    }

    private void deleteUser(final String userId) {
        delete("/users/" + userId)
                .then().assertThat()
                .statusCode(204);
    }
}
