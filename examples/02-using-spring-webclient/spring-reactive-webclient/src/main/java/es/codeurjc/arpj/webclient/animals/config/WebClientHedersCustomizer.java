package es.codeurjc.arpj.webclient.animals.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class WebClientHedersCustomizer implements WebClientCustomizer {

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder
                .defaultHeader("My-Custom-Header", "My-Custom-Content")
                .filter(logRequest());
    }

    private ExchangeFilterFunction logRequest() {
        return (req, next) -> {
            log.info("Request: {} {}", req.method(), req.url());
            req.headers().forEach((name, values) -> values.forEach(value -> log.info("[{}]={}", name, value)));
            return next.exchange(req);
        };
    }
}
