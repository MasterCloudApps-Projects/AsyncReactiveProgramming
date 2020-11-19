package es.codeurjc.arpj.webclient.characters.infrastructure.http_in;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(builderClassName = "Builder")
public class CharacterHttpResponse {

    private final Integer id;

    private final String name;

    private final String status;

    private final String species;

    private final String image;
}
