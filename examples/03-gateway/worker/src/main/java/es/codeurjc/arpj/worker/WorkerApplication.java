package es.codeurjc.arpj.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class WorkerApplication {

    public static void main(String[] args) {
        //BlockHound.install();
        SpringApplication.run(WorkerApplication.class, args);
    }
}
