package es.codeurjc.arpj.worker.characters.infrastructure.http_out;


import es.codeurjc.arpj.worker.characters.domain.Character;
import es.codeurjc.arpj.worker.characters.domain.CharacterId;
import es.codeurjc.arpj.worker.characters.domain.CharacterRepository;
import es.codeurjc.arpj.worker.characters.domain.CharacterStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Random;

@Repository
public class CharacterHttpRepository implements CharacterRepository {

    // private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Random RANDOM = new Random();

    private final WebClient webClient;

    public CharacterHttpRepository(@Value("${character.repository.url}") final String repoUrl) {
        webClient = WebClient.builder()
                .baseUrl(repoUrl)
                .build();
    }

    @Override
    public Mono<Character> random() {

        final int randomId = RANDOM.nextInt(500);
        return webClient.get()
                .uri("/character/" + randomId)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return CharacterHttpRepository.toCharacter(response);
                    } else {
                        return Mono.error(Exception::new);
                    }
                });
    }

    private static Mono<Character> toCharacter(final ClientResponse response) {
        return response
                .bodyToMono(CharacterHttpRepositoryResponse.class)
                .map(crr -> Character.builder()
                        .id(new CharacterId(crr.getId()))
                        .name(crr.getName())
                        .status(CharacterStatus.from(crr.getStatus()))
                        .species(crr.getSpecies())
                        .image(crr.getImage())
                        .build());
    }
}
