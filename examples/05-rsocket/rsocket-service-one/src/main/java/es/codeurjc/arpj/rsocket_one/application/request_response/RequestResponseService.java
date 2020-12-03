package es.codeurjc.arpj.rsocket_one.application.request_response;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RequestResponseService {

    private static final Faker FAKER = new Faker();

    public Mono<RequestResponseResponse> requestAndResponse(final Mono<RequestResponseCommand> command) {

        return command
                .doOnNext(r0 -> System.out.println("[2][" + r0.message() + "][" + r0.author() + "]"))
                .map(r1 -> "You, " + r1.author() + ", said: \"" + r1.message() + "\"")
                .map(r2 -> r2 + " And my response is: \"" + FAKER.yoda().quote() + "\"")
                .map(r3 -> new RequestResponseResponse(FAKER.buffy().characters(), r3));
    }
}
