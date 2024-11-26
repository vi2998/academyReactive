package it.reactive.academy.computer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

public class ConfigurazioneAdmin {
    @
    @Profile("ADMIN")

    @Bean
    public Tastiera tastiera(){
        return new Tastiera();
    }

}
