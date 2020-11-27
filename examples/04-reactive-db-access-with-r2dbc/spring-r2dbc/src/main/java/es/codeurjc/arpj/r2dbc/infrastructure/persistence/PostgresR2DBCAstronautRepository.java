package es.codeurjc.arpj.r2dbc.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostgresR2DBCAstronautRepository extends ReactiveCrudRepository<AstronautEntity, Long> {
    // PostgresR2DBCAstronautRepository
}
