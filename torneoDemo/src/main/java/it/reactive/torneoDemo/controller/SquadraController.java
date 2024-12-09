package it.reactive.torneoDemo.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.reactive.torneoDemo.DTO.squadra.SquadraDTO;
import it.reactive.torneoDemo.DTO.squadra.SquadraGiocatoreDTO;
import it.reactive.torneoDemo.DTO.tifoseria.TifoseriaDTO;
import it.reactive.torneoDemo.resource.Giocatore;
import it.reactive.torneoDemo.resource.Squadra;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "squadre", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SquadraController {

    @ApiOperation(value = "Creao una nuova squadra", response = Squadra.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Squadra creata con successo"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server")
    })
    @PostMapping("/salvaSquadra")
    public ResponseEntity<Squadra> salvaSquadra(@RequestBody SquadraDTO squadraDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


    @ApiOperation(value = "Creao una nuova squadra con la lista di giocatore", response = Squadra.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Squadra e giocatori creata con successo"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server")
    })
    @PostMapping("/salvaSquadraSquadraGiocatori")
    public ResponseEntity<Squadra> salvaSquadraSquadraGiocatori(@RequestBody SquadraGiocatoreDTO squadraGiocatoreDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


    @ApiOperation(value = "Ricerca squadra con lista giocatori", response = Squadra.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ricerca avvenuta con sucesso"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server")
    })
    @GetMapping("/ricercaSquadre/{completo}")
    public ResponseEntity<List<Squadra>> ricercaSquadra(@PathVariable @ApiParam("Parametro che mi inizializza una lista di giocatori vuota o meno") boolean completo) {
        return ResponseEntity.ok(null);
    }

    @ApiOperation(value = "Aggiungo una giocatore ad una determinata squadra", response = Squadra.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Squadre cercate", response = Squadra.class),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "errore di server")})
    @PutMapping("/aggiungiGiocatore/{idSquadra}")
    public ResponseEntity<Squadra> aggiungiGiocatore(@PathVariable Integer idSquadra, Giocatore giocatore) {
        return ResponseEntity.ok(null);
    }

    @ApiOperation(value = "Aggiorno una tifoseria se no ne creo una", response = Squadra.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tifoseria aggiornata o creata con sucesso", response = Squadra.class),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "errore di server")})
    @PutMapping("/aggiungiTifoseria/{idSquadra}")
    public ResponseEntity<Squadra> aggiungiTifoseria(@PathVariable Integer idSquadra,@RequestBody TifoseriaDTO tifoseriaDTO) {
        return ResponseEntity.ok(null);
    }



    @ApiOperation(value = "Elimino squadra con relativi giocatori", response = Squadra.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Squadra eliminata con sucesso", response = Squadra.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "errore di server")})
    @DeleteMapping("/eliminaSquadra/{idSquadra}")
    public ResponseEntity<List<Squadra>> rimuoviSquadra(@PathVariable Integer idSquadra) {
        return ResponseEntity.ok(null);
    }

}
