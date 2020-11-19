package es.codeurjc.arpj.webclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class WebclientApplication {

    public static void main(String[] args) {

        BlockHound.builder()
                .allowBlockingCallsInside("java.io.FileInputStream", "readBytes")
                .install();

        SpringApplication.run(WebclientApplication.class, args);
    }
}
