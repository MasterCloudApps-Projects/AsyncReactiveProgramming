package es.codeurjc.arpj.webfluxapi.application.find;

import es.codeurjc.arpj.webfluxapi.domain.UserId;
import es.codeurjc.arpj.webfluxapi.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserFinder {

    private final UserRepository repository;

    public Flux<UserFinderResponse> findAll() {
        return repository.findAll()
                .map(u -> new UserFinderResponse(u.getId(), u.getName(), u.getSurname(), u.getEmailAddress()));
    }

    public Mono<UserFinderResponse> findById(final UserFinderQuery query) {
        return repository.findOne(new UserId(query.id()))
                .map(u -> new UserFinderResponse(u.getId(), u.getName(), u.getSurname(), u.getEmailAddress()))
                .switchIfEmpty(Mono.empty());
    }

}
