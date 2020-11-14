package es.codeurjc.arpj.webfluxapi.application.create;

public record UserCreatorCommand(String id, String name, String surname, String email) {
}
