package es.codeurjc.arpj.rsocket_two.infrastructure.output.rsocket.channel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelRequest {

    private String message;

    private String author;
}
