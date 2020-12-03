package es.codeurjc.arpj.rsocket_two.infrastructure.output.rsocket.request_stream;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RequestStreamRequester {

    private static final Faker FAKER = new Faker();

    private final RSocketRequester requester;

    @EventListener(ApplicationReadyEvent.class)
    public void requestStream() {

        final var quote  = FAKER.elderScrolls().quote();
        final var author = FAKER.dune().character();

        requester.route("request-stream")
                .data(new RequestStreamRequest(quote, author))
                .retrieveFlux(RequestStreamRequest.class)
                .doOnNext(x -> System.out.println("[3][" + x.getMessage() + "][" + x.getAuthor() + "]"))
                .doOnSubscribe(x -> System.out.println("[3][Request stream requester subscription]"))
                .subscribe();
    }
}
