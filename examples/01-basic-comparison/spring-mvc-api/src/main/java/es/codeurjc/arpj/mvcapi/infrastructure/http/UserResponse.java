package es.codeurjc.arpj.mvcapi.infrastructure.http;

import lombok.Data;

@Data
public class UserResponse {

    private final String id;

    private final String name;

    private final String surname;

    private final String email;
}
