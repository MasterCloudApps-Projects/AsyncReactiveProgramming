package es.codeurjc.arpj.webfluxapi.application.update;

public record UserUpdaterCommand(String id, String name, String surname, String email) {
}
