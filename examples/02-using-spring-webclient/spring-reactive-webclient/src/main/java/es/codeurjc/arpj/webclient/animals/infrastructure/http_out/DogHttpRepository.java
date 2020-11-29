package es.codeurjc.arpj.webclient.animals.infrastructure.http_out;

import es.codeurjc.arpj.webclient.animals.domain.Dog;
import es.codeurjc.arpj.webclient.animals.domain.DogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class DogHttpRepository implements DogRepository {

    private final WebClient webClient;

    public DogHttpRepository(@Value("${dog.repository.url}") final String repoUrl,
                             final WebClient.Builder builder) {

        webClient = builder.baseUrl(repoUrl).build();
    }

    @Override
    public Mono<Dog> random() {
        return webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return DogHttpRepository.toDog(response);
                    } else {
                        return Mono.error(Exception::new);
                    }
                });
    }

    private static Mono<Dog> toDog(final ClientResponse response) {
        return response
                .bodyToMono(DogHttpRepositoryResponse.class)
                .map(dhrr -> new Dog(dhrr.getUrl()));
    }
}
