package es.codeurjc.arpj.webfluxapi.infrastructure.persistence;

import es.codeurjc.arpj.webfluxapi.domain.User;
import es.codeurjc.arpj.webfluxapi.domain.UserId;
import es.codeurjc.arpj.webfluxapi.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InMemoryUserRepository implements UserRepository {

    private final ConcurrentHashMap<String, User> repository = new ConcurrentHashMap<>();

    @Override
    public Flux<User> findAll() {
        return Flux.fromIterable(repository.values());
    }

    @Override
    public Mono<User> findOne(final UserId id) {
        return Mono.justOrEmpty(repository.get(id.getValue()));
    }

    @Override
    public Mono<Void> save(final User user) {
        if (repository.get(user.getId()) == null) {
            repository.put(user.getId(), user);
        } else {
            log.error("The user with id=[{}] already exists!!!", user.getId());
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> update(final User user) {
        if (repository.get(user.getId()) != null) {
            repository.computeIfPresent(user.getId(), (k, v) -> user);
        } else {
            log.error("The user with id=[{}] not exists!!!", user.getId());
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> delete(final UserId id) {
        if (repository.get(id.getValue()) != null) {
            repository.remove(id.getValue());
        } else {
            log.error("The user with id=[{}] not exists!!!", id);
        }
        return Mono.empty();
    }
}
