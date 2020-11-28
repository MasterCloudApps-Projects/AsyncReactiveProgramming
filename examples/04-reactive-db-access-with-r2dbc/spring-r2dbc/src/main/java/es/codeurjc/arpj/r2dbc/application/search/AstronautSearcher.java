package es.codeurjc.arpj.r2dbc.application.search;

import es.codeurjc.arpj.r2dbc.domain.Astronaut;
import es.codeurjc.arpj.r2dbc.domain.AstronautCriteria;
import es.codeurjc.arpj.r2dbc.domain.AstronautRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AstronautSearcher {

    private final AstronautRepository repository;

    public Flux<AstronautSearcherResponse> search(final AstronautSearchCommand command) {
        return repository.filter(new AstronautCriteria(command.name())).map(AstronautSearcher::toResponse);
    }

    private static AstronautSearcherResponse toResponse(final Astronaut astronaut) {

        return AstronautSearcherResponse.builder()
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
}
