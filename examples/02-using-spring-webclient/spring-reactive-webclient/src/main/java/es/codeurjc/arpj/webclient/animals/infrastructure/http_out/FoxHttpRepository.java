package es.codeurjc.arpj.webclient.animals.infrastructure.http_out;

import es.codeurjc.arpj.webclient.animals.domain.Fox;
import es.codeurjc.arpj.webclient.animals.domain.FoxRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class FoxHttpRepository implements FoxRepository {

    private final WebClient webClient;

    public FoxHttpRepository(@Qualifier("foxClient") final WebClient foxesClient) {
        this.webClient = foxesClient;
    }

    @Override
    public Mono<Fox> random() {
        return webClient.get()
                .uri("/")
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return FoxHttpRepository.toFox(response);
                    } else {
                        return Mono.error(Exception::new);
                    }
                });
    }

    private static Mono<Fox> toFox(final ClientResponse response) {
        return response
                .bodyToMono(FoxHttpRepositoryResponse.class)
                .map(fhrr -> new Fox(fhrr.getImage()));
    }
}
