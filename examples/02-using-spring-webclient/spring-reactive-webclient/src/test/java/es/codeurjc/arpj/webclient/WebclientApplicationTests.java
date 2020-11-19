package es.codeurjc.arpj.webclient;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WebclientApplicationTests {

    private static final Faker FAKER = new Faker();

    @Test
    void contextLoads() {
        final var text = FAKER.lorem().paragraph();
        assertThat(text).isNotBlank();
    }
}
