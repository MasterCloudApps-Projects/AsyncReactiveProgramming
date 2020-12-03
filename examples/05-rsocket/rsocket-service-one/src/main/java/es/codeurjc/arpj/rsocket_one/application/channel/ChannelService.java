package es.codeurjc.arpj.rsocket_one.application.channel;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

@Service
public class ChannelService {

    private static final Random RANDOM = new Random();

    private static final Faker FAKER = new Faker();

    public Flux<ChannelResponse> channel(final Flux<ChannelCommand> command) {

        return command
                .doOnNext(r0 -> System.out.println("[4][" + r0.message() + "][" + r0.author() + "]"))
                .map(r2 -> new ChannelResponse(FAKER.gameOfThrones().character(),
                        FAKER.shakespeare().romeoAndJulietQuote()))
                .delayElements(Duration.ofMillis(RANDOM.nextInt(30) * 250L));
    }
}
