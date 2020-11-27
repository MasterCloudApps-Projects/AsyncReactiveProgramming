package es.codeurjc.arpj.r2dbc.application.update;

import es.codeurjc.arpj.r2dbc.domain.Astronaut;
import es.codeurjc.arpj.r2dbc.domain.AstronautId;
import es.codeurjc.arpj.r2dbc.domain.AstronautRepository;
import es.codeurjc.arpj.r2dbc.shared.domain.Gender;
import es.codeurjc.arpj.r2dbc.shared.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AstronautUpdater {

    private final AstronautRepository repository;

    public Mono<Void> update(final AstronautUpdateCommand command) {
        return repository.update(AstronautUpdater.toDomain(command));
    }

    private static Astronaut toDomain(final AstronautUpdateCommand command) {

        return Astronaut.builder()
                .id(new AstronautId(command.id()))
                .name(command.name())
                .status(Status.from(command.status()))
                .birthPlace(command.birthPlace())
                .gender(Gender.from(command.gender()))
                .spaceFlights(command.spaceFlights())
                .spaceWalks(command.spaceWalks())
                .missions(command.missions())
                .build();
    }

}
