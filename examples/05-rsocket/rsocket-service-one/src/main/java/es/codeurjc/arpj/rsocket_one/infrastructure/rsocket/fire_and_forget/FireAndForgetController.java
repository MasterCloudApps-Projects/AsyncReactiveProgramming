package es.codeurjc.arpj.rsocket_one.infrastructure.rsocket.fire_and_forget;

import es.codeurjc.arpj.rsocket_one.application.fire_and_forget.FireAndForgetCommand;
import es.codeurjc.arpj.rsocket_one.application.fire_and_forget.FireAndForgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FireAndForgetController {

    private final FireAndForgetService service;

    @MessageMapping("fire-and-forget")
    public void fireAndForget(final FireAndForgetRequest request) {
        service.start(Mono.just(new FireAndForgetCommand(request.getAuthor(), request.getMessage()))).subscribe();
    }
}
