package it.reactive.academy.computer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("Admin")

public class ConfigurazioneAdmin {
    /*Creare una classe ConfigurazioneAdmin caricata come Bean solo dal profilo ADMIN.
    In questa classe configurare l’uso della tastiera*/

    @Bean
    public Tastiera tastiera(){
        return new Tastiera();
    }

}
