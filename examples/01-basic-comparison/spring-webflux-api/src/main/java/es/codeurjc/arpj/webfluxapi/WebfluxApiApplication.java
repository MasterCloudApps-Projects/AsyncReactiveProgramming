package es.codeurjc.arpj.webfluxapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class WebfluxApiApplication {

    public static void main(String[] args) {
        BlockHound.install();
        SpringApplication.run(WebfluxApiApplication.class, args);
    }
}
