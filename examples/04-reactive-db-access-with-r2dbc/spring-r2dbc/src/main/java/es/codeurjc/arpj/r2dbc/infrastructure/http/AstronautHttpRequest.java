package es.codeurjc.arpj.r2dbc.infrastructure.http;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AstronautHttpRequest {

    private Long id;

    private String name;

    private String status;

    private String birthPlace;

    private String gender;

    private int spaceFlights;

    private int spaceWalks;

    private String missions;
}
