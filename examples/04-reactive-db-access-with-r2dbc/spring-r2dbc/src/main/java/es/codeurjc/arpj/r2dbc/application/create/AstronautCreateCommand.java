package es.codeurjc.arpj.r2dbc.application.create;

public record AstronautCreateCommand(String name, String status, String birthPlace, String gender, int spaceFlights,
                                     int spaceWalks, String missions) {
}
