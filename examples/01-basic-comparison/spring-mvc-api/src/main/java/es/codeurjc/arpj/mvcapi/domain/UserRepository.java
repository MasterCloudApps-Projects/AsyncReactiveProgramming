package es.codeurjc.arpj.mvcapi.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findOne(UserId id);

    void save(User user);

    void update(User user);

    void delete(UserId id);
}
