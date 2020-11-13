package es.codeurjc.arpj.mvcapi.application.find;

import es.codeurjc.arpj.mvcapi.domain.UserId;
import es.codeurjc.arpj.mvcapi.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFinder {

    private final UserRepository repository;


    public List<UserFinderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(u -> new UserFinderResponse(u.getId(), u.getName(), u.getSurname(), u.getEmailAddress()))
                .collect(Collectors.toList());
    }

    public UserFinderResponse findById(final UserFinderQuery query) {
        return repository.findOne(new UserId(query.id()))
                .map(u -> new UserFinderResponse(u.getId(), u.getName(), u.getSurname(), u.getEmailAddress()))
                .orElse(null);
    }

}
