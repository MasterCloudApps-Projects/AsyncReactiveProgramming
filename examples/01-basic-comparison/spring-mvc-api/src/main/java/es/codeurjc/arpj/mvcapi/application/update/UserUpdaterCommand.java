package es.codeurjc.arpj.mvcapi.application.update;

public record UserUpdaterCommand(String id, String name, String surname, String email) {
}
