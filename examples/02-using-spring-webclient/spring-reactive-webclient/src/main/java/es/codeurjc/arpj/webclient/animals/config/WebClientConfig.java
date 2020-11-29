package es.codeurjc.arpj.webclient.animals.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${cat.repository.url}")
    private String catsUrl;

    @Value("${dog.repository.url}")
    private String dogsUrl;

    @Value("${fox.repository.url}")
    private String foxesUrl;

    @Bean("catClient")
    public WebClient catWebClient(final WebClient.Builder builder) {
        return builder.baseUrl(catsUrl).build();
    }

    @Bean("dogClient")
    public WebClient dogWebClient(final WebClient.Builder builder) {
        return builder.baseUrl(dogsUrl).build();
    }

    @Bean("foxClient")
    public WebClient foxWebClient(final WebClient.Builder builder) {
        return builder.baseUrl(foxesUrl).build();
    }
}
