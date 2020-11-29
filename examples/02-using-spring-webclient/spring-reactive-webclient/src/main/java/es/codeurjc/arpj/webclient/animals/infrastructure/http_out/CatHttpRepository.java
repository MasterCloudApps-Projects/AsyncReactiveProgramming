package es.codeurjc.arpj.webclient.animals.infrastructure.http_out;

import es.codeurjc.arpj.webclient.animals.domain.Cat;
import es.codeurjc.arpj.webclient.animals.domain.CatRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class CatHttpRepository implements CatRepository {

    private final String    repoUrl;

    public CatHttpRepository(@Value("${cat.repository.url}") final String repoUrl) {
        this.repoUrl = repoUrl;
    }

    @Override
    public Mono<Cat> random() {
        return WebClient.create()
                .get()
                .uri(repoUrl)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return CatHttpRepository.toCat(response);
                    } else {
                        return Mono.error(Exception::new);
                    }
                });
    }

    private static Mono<Cat> toCat(final ClientResponse response) {
        return response
                .bodyToMono(CatHttpRepositoryResponse.class)
                .map(chrr -> new Cat(chrr.getFile()));
    }
}
