package es.codeurjc.arpj.rsocket_one.application.fire_and_forget;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FireAndForgetService {

    public Mono<Void> start(final Mono<FireAndForgetCommand> command) {
        return command.doOnNext(this::printer).then();
    }

    private void printer(final FireAndForgetCommand command) {
        System.out.println("[1][" + command.message() + "][" + command.author() + "]");
    }

}
