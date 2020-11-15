package es.codeurjc.arpj.mvcapi.infrastructure.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String id;

    private String name;

    private String surname;

    private String email;
}
