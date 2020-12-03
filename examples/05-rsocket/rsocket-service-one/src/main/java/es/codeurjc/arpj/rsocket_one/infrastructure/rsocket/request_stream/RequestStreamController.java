package es.codeurjc.arpj.rsocket_one.infrastructure.rsocket.request_stream;

import es.codeurjc.arpj.rsocket_one.application.request_stream.RequestStreamCommand;
import es.codeurjc.arpj.rsocket_one.application.request_stream.RequestStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RequestStreamController {

    private final RequestStreamService service;

    @MessageMapping("request-stream")
    public Flux<RequestStreamRequest> requestStream(final RequestStreamRequest req) {
        return service.requestStream(Mono.just(
                new RequestStreamCommand(req.getAuthor(), req.getMessage())))
                .map(r -> new RequestStreamRequest(r.message(), r.author()));
    }
}
