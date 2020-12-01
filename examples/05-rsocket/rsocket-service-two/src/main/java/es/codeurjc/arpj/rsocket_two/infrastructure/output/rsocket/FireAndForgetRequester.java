package es.codeurjc.arpj.rsocket_two.infrastructure.output.rsocket;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FireAndForgetRequester {

    private static final Faker FAKER = new Faker();

    private final RSocketRequester requester;

    @EventListener(ApplicationReadyEvent.class)
    public void fireAndForget() {

        requester.route("fire-and-forget")
                .data(new FireAndForgetRequest(FAKER.yoda().quote(), FAKER.artist().name()))
                .send()
                .doOnSubscribe(x -> System.out.println("[1][Fire and forget requester subscription]"))
                .subscribe();
    }

}
