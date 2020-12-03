package es.codeurjc.arpj.rsocket_two.infrastructure.output.rsocket.channel;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@Component
@AllArgsConstructor
public class ChannelRequester {

    private static final Faker FAKER = new Faker();

    private final RSocketRequester requester;

    @EventListener(ApplicationReadyEvent.class)
    public void channel() {

        requester.route("channel")
                .data(Flux
                        .range(0, 100)
                        .delayElements(Duration.ofMillis(350))
                        .map(x -> new ChannelRequest(FAKER.shakespeare().asYouLikeItQuote(),
                                FAKER.funnyName().name())))
                .retrieveFlux(ChannelRequest.class)
                .doOnNext(x -> System.out.println("[4][" + x.getMessage() + "][" + x.getAuthor() + "]"))
                .doOnSubscribe(x -> System.out.println("[4][Channel requester subscription]"))
                .subscribe();
    }
}
