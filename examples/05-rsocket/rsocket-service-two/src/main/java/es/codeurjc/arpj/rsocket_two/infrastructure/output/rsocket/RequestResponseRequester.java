package es.codeurjc.arpj.rsocket_two.infrastructure.output.rsocket;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RequestResponseRequester {

    private static final Faker FAKER = new Faker();

    private final RSocketRequester requester;

    @EventListener(ApplicationReadyEvent.class)
    public void fireAndForget() {

        final var quote  = FAKER.shakespeare().kingRichardIIIQuote();
        final var author = FAKER.dragonBall().character();

        requester.route("request-response")
                .data(new RequestResponseRequest(quote, author))
                .retrieveMono(RequestResponseRequest.class)
                .doOnNext(x -> System.out.println("[2][" + x.getMessage() + "][" + x.getAuthor() + "]"))
                .doOnSubscribe(x -> System.out.println("[2][Request response requester subscription]"))
                .subscribe();
    }
}
