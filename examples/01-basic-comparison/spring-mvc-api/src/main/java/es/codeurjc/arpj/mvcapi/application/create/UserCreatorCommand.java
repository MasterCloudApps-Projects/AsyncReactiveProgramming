package es.codeurjc.arpj.mvcapi.application.create;

public record UserCreatorCommand(String id, String name, String surname, String email) {
}
