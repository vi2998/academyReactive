package it.reactive.torneoDemoMongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class TorneoDemoMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TorneoDemoMongoApplication.class, args);
    }

}
