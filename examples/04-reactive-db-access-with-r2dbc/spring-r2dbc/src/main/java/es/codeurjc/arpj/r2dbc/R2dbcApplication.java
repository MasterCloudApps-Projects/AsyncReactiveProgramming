package es.codeurjc.arpj.r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class R2dbcApplication {

    public static void main(String[] args) {
        BlockHound.install();
        SpringApplication.run(R2dbcApplication.class, args);
    }
}