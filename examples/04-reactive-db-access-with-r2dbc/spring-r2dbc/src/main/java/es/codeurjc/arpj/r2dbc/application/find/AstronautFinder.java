package es.codeurjc.arpj.r2dbc.application.find;

import es.codeurjc.arpj.r2dbc.domain.Astronaut;
import es.codeurjc.arpj.r2dbc.domain.AstronautId;
import es.codeurjc.arpj.r2dbc.domain.AstronautRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AstronautFinder {

    private final AstronautRepository repository;

    public Flux<AstronautFinderResponse> findAll() {
        return repository.findAll().map(AstronautFinder::toHttpResponse);
    }

    public Mono<AstronautFinderResponse> findById(final Long id) {
        return repository.findById(new AstronautId(id)).map(AstronautFinder::toHttpResponse);
    }

    public Mono<AstronautFinderResponse> random() {
        return repository.random().map(AstronautFinder::toHttpResponse);
    }

    private static AstronautFinderResponse toHttpResponse(final Astronaut astronaut) {

        return AstronautFinderResponse.builder()
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
