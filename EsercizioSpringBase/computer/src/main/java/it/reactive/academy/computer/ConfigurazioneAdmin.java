package it.reactive.academy.computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("Admin")

public class ConfigurazioneAdmin {
    @Bean
    public Tastiera tastiera() {
        return new Tastiera();
    }
}