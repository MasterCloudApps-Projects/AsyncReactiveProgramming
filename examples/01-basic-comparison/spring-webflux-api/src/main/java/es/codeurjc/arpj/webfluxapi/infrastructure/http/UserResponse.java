package es.codeurjc.arpj.webfluxapi.infrastructure.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;

    private String name;

    private String surname;

    private String email;
}
