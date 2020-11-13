package es.codeurjc.arpj.mvcapi.application.create;

import es.codeurjc.arpj.mvcapi.domain.EmailAddress;
import es.codeurjc.arpj.mvcapi.domain.User;
import es.codeurjc.arpj.mvcapi.domain.UserId;
import es.codeurjc.arpj.mvcapi.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreator {

    private final UserRepository repository;

    public void create(final UserCreatorCommand cmd){
        final User user = new User(new UserId(cmd.id()), cmd.name(), cmd.surname(),new EmailAddress(cmd.email()));
        repository.save(user);
    }
}
