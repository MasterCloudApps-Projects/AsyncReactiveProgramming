package es.codeurjc.arpj.mvcapi.application.remove;

import es.codeurjc.arpj.mvcapi.domain.UserId;
import es.codeurjc.arpj.mvcapi.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRemover {

    private final UserRepository repository;

    public void remove(final UserRemoverCommand cmd) {
        repository.delete(new UserId(cmd.id()));
    }
}
