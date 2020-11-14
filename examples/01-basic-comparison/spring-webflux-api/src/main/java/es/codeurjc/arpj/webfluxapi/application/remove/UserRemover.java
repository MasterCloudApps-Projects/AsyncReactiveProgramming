package es.codeurjc.arpj.webfluxapi.application.remove;

import es.codeurjc.arpj.webfluxapi.domain.UserId;
import es.codeurjc.arpj.webfluxapi.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserRemover {

    private final UserRepository repository;

    public Mono<Void> remove(final UserRemoverCommand cmd) {
        return repository.delete(new UserId(cmd.id()));
    }
}
