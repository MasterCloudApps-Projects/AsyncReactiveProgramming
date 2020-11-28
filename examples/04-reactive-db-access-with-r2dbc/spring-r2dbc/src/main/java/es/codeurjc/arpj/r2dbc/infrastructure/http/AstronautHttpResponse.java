package es.codeurjc.arpj.r2dbc.infrastructure.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AstronautHttpResponse {

    private Long id;

    private String name;

    private String status;

    private String birthPlace;

    private String gender;

    private int spaceFlights;

    private int spaceWalks;

    private String missions;
}
