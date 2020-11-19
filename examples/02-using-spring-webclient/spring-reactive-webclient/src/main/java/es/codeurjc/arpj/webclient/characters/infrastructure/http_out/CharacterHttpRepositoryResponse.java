package es.codeurjc.arpj.webclient.characters.infrastructure.http_out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterHttpRepositoryResponse {

    private Integer id;

    private String name;

    private String status;

    private String species;

    private String image;
}
