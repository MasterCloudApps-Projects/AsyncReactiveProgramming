package es.codeurjc.arpj.webclient.animals.infrastructure.http_in;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(builderClassName = "Builder")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimalGroupHttpResponse {

    private final String cat;

    private final String dog;

    private final String fox;
}
