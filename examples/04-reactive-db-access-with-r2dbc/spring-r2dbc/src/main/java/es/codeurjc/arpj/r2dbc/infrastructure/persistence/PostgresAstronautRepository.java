package es.codeurjc.arpj.r2dbc.infrastructure.persistence;

import es.codeurjc.arpj.r2dbc.domain.Astronaut;
import es.codeurjc.arpj.r2dbc.domain.AstronautCriteria;
import es.codeurjc.arpj.r2dbc.domain.AstronautId;
import es.codeurjc.arpj.r2dbc.domain.AstronautRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

@Repository
@RequiredArgsConstructor
public class PostgresAstronautRepository implements AstronautRepository {

    private static final Random RANDOM = new Random();

    private final PostgresR2DBCAstronautRepository reactiveRepository;

    @Override
    public Flux<Astronaut> findAll() {
        return reactiveRepository.findAll().map(AstronautEntityMapper::entityToDomain);
    }

    @Override
    public Mono<Astronaut> findById(AstronautId id) {
        return reactiveRepository.findById(id.getValue()).map(AstronautEntityMapper::entityToDomain);
    }

    @Override
    public Mono<Astronaut> random() {
        return reactiveRepository
                .findAll()
                .map(AstronautEntity::getId)
                .collectList()
                .flatMap(l -> reactiveRepository
                        .findById(l.get(RANDOM.nextInt(l.size())))
                        .map(AstronautEntityMapper::entityToDomain));
    }

    @Override
    public Mono<Void> delete(final AstronautId id) {
        return reactiveRepository.deleteById(id.getValue());
    }

    @Override
    public Mono<Astronaut> save(final Astronaut astronaut) {
        return reactiveRepository
                .save(AstronautEntityMapper.domainToEntity(astronaut))
                .map(AstronautEntityMapper::entityToDomain);
    }

    @Override
    public Mono<Void> update(final Astronaut astronaut) {
        return reactiveRepository.save(AstronautEntityMapper.domainToEntity(astronaut)).then();
    }

    @Override
    public Flux<Astronaut> filter(final AstronautCriteria criteria) {
        return reactiveRepository
                .findAllByNameContaining(criteria.getName())
                .map(AstronautEntityMapper::entityToDomain);
    }
}
