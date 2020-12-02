package es.codeurjc.arpj.rsocket_one.infrastructure.rsocket.request_response;

import es.codeurjc.arpj.rsocket_one.application.request_response.RequestResponseCommand;
import es.codeurjc.arpj.rsocket_one.application.request_response.RequestResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RequestResponseController {

    private final RequestResponseService service;

    @MessageMapping("request-response")
    public Mono<RequestResponseRequest> fireAndForget(final RequestResponseRequest req) {
        return service.requestAndResponse(Mono.just(new RequestResponseCommand(req.getAuthor(), req.getMessage())))
                .map(r -> new RequestResponseRequest(r.message(), r.author()));
    }
}
