package it.reactive.academy.computer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurazione {

    /*Creare la classe di configurazione Configurazione per creare il Bean HardDisk
    partendo dalla variabile d’ambiente.
    Se questa vale SSD restituisce un Bean di tipo SSD,
    se vale HD un Bean di tipo HD
    altrimenti segnalare che la variabile d’ambiente è configurata in maniera errata. */

    @Value("${harddisk}")
    private String tipoHardDisk;

    @Bean
    HardDisk creaHardDisk() {
        if(tipoHardDisk.equals("SSD")) {
            return new SSD();
        } else if (tipoHardDisk.equals("HD")) {
            return new HD();
        } else {
            throw new RuntimeException("Tipo di hard disk configurato in maniera errata.");
        }
    }
}
