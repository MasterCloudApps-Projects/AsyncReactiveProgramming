package es.codeurjc.arpj.r2dbc.application.remove;

import es.codeurjc.arpj.r2dbc.domain.AstronautId;
import es.codeurjc.arpj.r2dbc.domain.AstronautRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AstronautRemover {

    private final AstronautRepository repository;

    public Mono<Void> remove(final Long id) {
        return repository.delete(new AstronautId(id));
    }

}
