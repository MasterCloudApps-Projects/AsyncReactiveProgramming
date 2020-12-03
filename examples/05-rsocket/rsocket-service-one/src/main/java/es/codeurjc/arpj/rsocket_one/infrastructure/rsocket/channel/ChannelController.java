package es.codeurjc.arpj.rsocket_one.infrastructure.rsocket.channel;

import es.codeurjc.arpj.rsocket_one.application.channel.ChannelCommand;
import es.codeurjc.arpj.rsocket_one.application.channel.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService service;

    @MessageMapping("channel")
    public Flux<ChannelRequest> channel(final Flux<ChannelRequest> reqs) {
        return service.channel(reqs
                .map(r -> new ChannelCommand(r.getAuthor(), r.getMessage())))
                .map(r -> new ChannelRequest(r.message(), r.author()));
    }
}
