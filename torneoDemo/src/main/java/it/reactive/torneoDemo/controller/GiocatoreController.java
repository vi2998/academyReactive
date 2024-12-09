package it.reactive.torneoDemo.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.reactive.torneoDemo.resource.Giocatore;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "giocatore", produces = {MediaType.APPLICATION_JSON_VALUE})
public class GiocatoreController {

    // per documentazione SWAGGER
    @ApiOperation(value = "Aggiorna ammonizione per un determinato giocatore", response = Giocatore.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Aggiorna ammonizione", response = Giocatore.class),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore di server")})
    @PutMapping("/ammonizione/{idGiocatore}")
    public ResponseEntity<Giocatore> aggiornaAmmonizione(@PathVariable Integer idGiocatore) {
        return ResponseEntity.ok(null);
    }

}
