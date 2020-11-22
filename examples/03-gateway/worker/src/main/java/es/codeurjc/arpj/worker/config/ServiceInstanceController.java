package es.codeurjc.arpj.worker.config;

import com.netflix.appinfo.InstanceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@RequiredArgsConstructor
public class ServiceInstanceController {

    @Bean
    RouterFunction<ServerResponse> configRoutes(final DiscoveryClient discoveryClient) {

        return route()

                .GET("/service-instances/{applicationName}",
                        req -> ok().body(Mono.just(discoveryClient.getInstances(req.pathVariable("applicationName"))),
                                InstanceInfo.class))
                .build();
    }

}
