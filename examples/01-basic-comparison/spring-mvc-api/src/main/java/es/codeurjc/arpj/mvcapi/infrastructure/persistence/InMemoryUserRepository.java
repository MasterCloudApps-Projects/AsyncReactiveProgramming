package es.codeurjc.arpj.mvcapi.infrastructure.persistence;

import es.codeurjc.arpj.mvcapi.domain.User;
import es.codeurjc.arpj.mvcapi.domain.UserId;
import es.codeurjc.arpj.mvcapi.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InMemoryUserRepository implements UserRepository {

    private final ConcurrentHashMap<String, User> repository = new ConcurrentHashMap<>();

    @Override
    public List<User> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<User> findOne(final UserId id) {
        return Optional.ofNullable(repository.get(id.getValue()));
    }

    @Override
    public void save(final User user) {
        if (repository.get(user.getId()) == null) {
            repository.put(user.getId(), user);
        } else {
            log.error("The user with id={} already exists!!!", user.getId());
        }
    }

    @Override
    public void update(final User user) {
        if (repository.get(user.getId()) != null) {
            repository.computeIfPresent(user.getId(), (k, v) -> user);
        } else {
            log.error("The user with id={} not exists!!!", user.getId());
        }
    }

    @Override
    public void delete(final UserId id) {
        if (repository.get(id.getValue()) != null) {
            repository.remove(id.getValue());
        } else {
            log.error("The user with id={} not exists!!!", id);
        }
    }
}
