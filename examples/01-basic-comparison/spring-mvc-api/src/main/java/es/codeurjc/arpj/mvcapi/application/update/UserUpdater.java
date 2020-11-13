package es.codeurjc.arpj.mvcapi.application.update;

import es.codeurjc.arpj.mvcapi.domain.EmailAddress;
import es.codeurjc.arpj.mvcapi.domain.User;
import es.codeurjc.arpj.mvcapi.domain.UserId;
import es.codeurjc.arpj.mvcapi.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdater {

    private final UserRepository repository;

    public void update(final UserUpdaterCommand cmd) {
        repository.update(new User(new UserId(cmd.id()), cmd.name(), cmd.surname(), new EmailAddress(cmd.email())));
    }
}
