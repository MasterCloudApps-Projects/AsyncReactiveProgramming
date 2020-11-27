package es.codeurjc.arpj.r2dbc.application.create;

import es.codeurjc.arpj.r2dbc.domain.Astronaut;
import es.codeurjc.arpj.r2dbc.domain.AstronautRepository;
import es.codeurjc.arpj.r2dbc.shared.domain.Gender;
import es.codeurjc.arpj.r2dbc.shared.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AstronautCreator {

    private final AstronautRepository repository;

    public Mono<AstronautCreatorResponse> create(final AstronautCreateCommand command) {
        return repository.save(AstronautCreator.toDomain(command)).map(AstronautCreator::toHttpResponse);
    }

    private static AstronautCreatorResponse toHttpResponse(final Astronaut astronaut) {

        return AstronautCreatorResponse.builder()
                .id(astronaut.getId())
                .name(astronaut.getName())
                .status(astronaut.getStatus() != null ? astronaut.getStatus().getName() : "Unknown")
                .birthPlace(astronaut.getBirthPlace())
                .gender(astronaut.getGender() != null ? astronaut.getGender().getName() : "Unknown")
                .spaceFlights(astronaut.getSpaceFlights())
                .spaceWalks(astronaut.getSpaceWalks())
                .missions(astronaut.getMissions())
                .build();
    }

    private static Astronaut toDomain(final AstronautCreateCommand command) {

        return Astronaut.builder()
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
