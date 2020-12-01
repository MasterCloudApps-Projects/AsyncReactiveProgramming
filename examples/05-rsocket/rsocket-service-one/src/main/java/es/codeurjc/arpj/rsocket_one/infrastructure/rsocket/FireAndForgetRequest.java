package es.codeurjc.arpj.rsocket_one.infrastructure.rsocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireAndForgetRequest {

    private String message;

    private String author;
}
