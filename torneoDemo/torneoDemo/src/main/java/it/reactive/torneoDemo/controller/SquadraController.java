package it.reactive.torneoDemo.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.resource.Squadra;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "squadre", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SquadraController {

    // TODO autowired del squadraService

    @ApiOperation(value = "Aggiungi una nuova squadra", response = Squadra.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Squadra aggiunta"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server")
    })
    @PostMapping("/salvaSquadra")
    public ResponseEntity<Squadra> salvaSquadra(SquadraDTO squadraDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(null);

    }


}
