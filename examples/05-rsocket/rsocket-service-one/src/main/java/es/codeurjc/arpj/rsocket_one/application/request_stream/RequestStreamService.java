package es.codeurjc.arpj.rsocket_one.application.request_stream;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@Service
public class RequestStreamService {

    private static final Random RANDOM = new Random();

    private static final Faker FAKER = new Faker();

    public Flux<RequestStreamResponse> requestStream(final Mono<RequestStreamCommand> command) {

        final var myResponse = Flux.range(0, 50)
                .delayElements(Duration.ofMillis(RANDOM.nextInt(20) * 300L))
                .map(i -> new RequestStreamResponse(FAKER.zelda().character(), FAKER.chuckNorris().fact()));

        return command
                .doOnNext(x -> System.out.println("[3][" + x.message() + "][" + x.author() + "]"))
                .thenMany(myResponse);
    }
}
