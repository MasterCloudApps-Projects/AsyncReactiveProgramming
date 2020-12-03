package es.codeurjc.arpj.rsocket_two.infrastructure.output.rsocket.request_response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestResponseRequest {

    private String message;

    private String author;
}
