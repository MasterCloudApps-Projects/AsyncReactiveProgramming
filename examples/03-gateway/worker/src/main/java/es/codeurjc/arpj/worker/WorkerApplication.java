package es.codeurjc.arpj.worker;

import es.codeurjc.arpj.worker.config.EurekaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties(EurekaProperties.class)
public class WorkerApplication {

    public static void main(String[] args) {
        //BlockHound.install();
        SpringApplication.run(WorkerApplication.class, args);
    }
}
