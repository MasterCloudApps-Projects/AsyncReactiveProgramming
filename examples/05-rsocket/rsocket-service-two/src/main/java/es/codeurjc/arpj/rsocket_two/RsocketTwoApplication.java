package es.codeurjc.arpj.rsocket_two;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;

@SpringBootApplication
public class RsocketTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsocketTwoApplication.class, args);
    }

    @Bean
    RSocketRequester rSocketRequester(RSocketRequester.Builder builder) {
        return builder.tcp("localhost", 8081);
    }
}
