package es.codeurjc.arpj.webfluxapi.application.create;

import es.codeurjc.arpj.webfluxapi.domain.EmailAddress;
import es.codeurjc.arpj.webfluxapi.domain.User;
import es.codeurjc.arpj.webfluxapi.domain.UserId;
import es.codeurjc.arpj.webfluxapi.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserCreator {

    private final UserRepository repository;

    public Mono<Void> create(final UserCreatorCommand cmd) {
        final User user = new User(new UserId(cmd.id()), cmd.name(), cmd.surname(), new EmailAddress(cmd.email()));
        return repository.save(user);
    }
}
