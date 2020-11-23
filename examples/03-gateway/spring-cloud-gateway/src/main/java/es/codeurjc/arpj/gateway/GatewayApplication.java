package es.codeurjc.arpj.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    private static final String URI = "lb://worker-service";

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/random-character")
                        .filters(f -> f
                                .rewritePath("/random-character", "/characters/random")
                        )
                        .uri(URI))
                .route(p -> p
                        .path("/random-character-delay")
                        .filters(f -> f
                                .rewritePath("/random-character-delay", "/characters/random/with-delay")
                        )
                        .uri(URI))
                .route(p -> p
                        .path("/random-character-error")
                        .filters(f -> f
                                .rewritePath("/random-character-error", "/characters/random/with-errors")
                                .retry(3)
                        )
                        .uri(URI))
                .build();
    }
}
