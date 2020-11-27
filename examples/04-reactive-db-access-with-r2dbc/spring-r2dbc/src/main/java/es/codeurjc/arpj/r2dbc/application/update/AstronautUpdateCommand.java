package es.codeurjc.arpj.r2dbc.application.update;

public record AstronautUpdateCommand(Long id, String name, String status, String birthPlace, String gender,
                                     int spaceFlights, int spaceWalks, String missions) {
}
