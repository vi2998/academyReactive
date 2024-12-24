package it.reactive.torneoDemo.controller;

import it.reactive.torneoDemo.resource.NomeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NomeController {

    // aggiunta di una get per il nome
    @GetMapping("/applicazione")
    public NomeResponse getNome() {
        return new NomeResponse("Pitrelli");
    }
}
