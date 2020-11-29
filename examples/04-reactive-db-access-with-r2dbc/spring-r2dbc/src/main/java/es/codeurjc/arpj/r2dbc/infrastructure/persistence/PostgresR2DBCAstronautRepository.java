package es.codeurjc.arpj.r2dbc.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PostgresR2DBCAstronautRepository
        extends ReactiveCrudRepository<AstronautEntity, Long> {

    Flux<AstronautEntity> findAllByNameContaining(String name);
}
