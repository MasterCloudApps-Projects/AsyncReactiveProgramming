package es.codeurjc.arpj.r2dbc.infrastructure.persistence;

import es.codeurjc.arpj.r2dbc.domain.Astronaut;
import es.codeurjc.arpj.r2dbc.domain.AstronautId;
import es.codeurjc.arpj.r2dbc.shared.domain.Gender;
import es.codeurjc.arpj.r2dbc.shared.domain.Status;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class AstronautEntityMapper {

    public static Astronaut entityToDomain(final AstronautEntity entity) {

        return Astronaut.builder()
                .id(new AstronautId(entity.getId()))
                .name(entity.getName())
                .birthPlace(entity.getBirthPlace())
                .missions(entity.getMissions())
                .spaceFlights(entity.getSpaceFlights())
                .spaceWalks(entity.getSpaceWalks())
                .gender(Gender.from(entity.getGender()))
                .status(Status.from(entity.getStatus()))
                .build();
    }

    public static AstronautEntity domainToEntity(final Astronaut domain) {

        final AstronautEntity entity = new AstronautEntity();

        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setBirthPlace(domain.getBirthPlace());
        entity.setMissions(domain.getMissions());
        entity.setSpaceFlights(domain.getSpaceFlights());
        entity.setSpaceWalks(domain.getSpaceWalks());
        entity.setStatus(domain.getStatus() != null ? domain.getStatus().getValue() : null);
        entity.setGender(domain.getGender() != null ? domain.getGender().getValue() : null);

        return entity;
    }
}
