package es.codeurjc.arpj.r2dbc;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class R2dbcApplicationTests {

    private static final Faker FAKER = new Faker();

    @Test
    void contextLoads() {
        final var text = FAKER.lorem().paragraph();
        assertThat(text).isNotBlank();
    }
}
