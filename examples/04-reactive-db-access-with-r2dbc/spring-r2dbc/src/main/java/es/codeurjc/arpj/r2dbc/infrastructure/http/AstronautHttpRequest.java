package es.codeurjc.arpj.r2dbc.infrastructure.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(builderClassName = "Builder")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AstronautHttpRequest {

    private final Long id;

    private final String name;

    private final String status;

    private final String birthPlace;

    private final String gender;

    private final int spaceFlights;

    private final int spaceWalks;

    private final String missions;
}
