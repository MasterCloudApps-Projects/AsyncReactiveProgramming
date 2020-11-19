package es.codeurjc.arpj.webclient;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CharacterSSETest {

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("Get character server sent events (5)")
    void random_character_server_sent_events() {

        List<String> events = webClient
                .get()
                .uri("/characters/random-list")
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody()
                .take(5)
                .collectList()
                .block();

        Objects.requireNonNull(events).forEach(command -> log.info("SSE: {}", command));
        assertThat(events.size()).isEqualTo(5);
    }
}
