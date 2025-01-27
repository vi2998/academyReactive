package it.reactive.torneoDemoMongo.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.reactive.torneoDemoMongo.dto.GiocatoreDTO;
import it.reactive.torneoDemoMongo.dto.SquadraDTO;
import it.reactive.torneoDemoMongo.dto.SquadreDiGiocatoriDTO;
import it.reactive.torneoDemoMongo.dto.TifoseriaDTO;
import it.reactive.torneoDemoMongo.resource.EccezioneResponse;
import it.reactive.torneoDemoMongo.resource.SquadraResponse;
import it.reactive.torneoDemoMongo.service.SquadraService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "squadre", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class SquadraController {
    @Autowired
    SquadraService squadraService;

    @ApiOperation(value = "Inserisce una nuova squadra", response = SquadraResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Squadra creata con successo"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server"),
            @ApiResponse(code = 550, message = "Squadra duplicata", response = EccezioneResponse.class)
    })
    @PostMapping
    public ResponseEntity<SquadraResponse> salvaSquadra(@RequestBody @Valid SquadraDTO squadraDTO) throws SQLException {
        return ResponseEntity.status(HttpStatus.CREATED).body(squadraService.salvaSquadra(squadraDTO));
    }


    @ApiOperation(value = "Inserisce una nuova squadra insieme a una lista di giocatori", response = SquadraResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Squadra e giocatori creata con successo", response = SquadraResponse.class),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            //@ApiResponse(code = 500, message = "Errore del server"),
            @ApiResponse(code = 550, message = "\t\n" +
                    "Il servizio va in errore con i cod:\n" +
                    "\n" +
                    "• C1 in caso di squadra già censita\n" +
                    "• C6 in caso di errore di validazione", response = EccezioneResponse.class)
    })
    @PostMapping("/squadreGiocatori")
    public ResponseEntity<SquadraResponse> salvaSquadraSquadraGiocatori(@RequestBody @Valid SquadreDiGiocatoriDTO squadreDiGiocatoriDTO) throws SQLException {
        return ResponseEntity.status(HttpStatus.CREATED).body(squadraService.salvaSquadraConGiocatori(squadreDiGiocatoriDTO));
    }


    @ApiOperation(value = "Ricerca squadra con lista giocatori", response = SquadraResponse.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ricerca avvenuta con sucesso"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server")
    })
    @GetMapping
    public ResponseEntity<List<SquadraResponse>> ricercaSquadre(@RequestParam @ApiParam("Parametro che mi inizializza una lista di giocatori vuota o meno") boolean completo) throws SQLException {
        return ResponseEntity.ok(squadraService.ricercaSquadre(completo));
    }

    @ApiOperation(value = "Aggiungo una giocatore ad una determinata squadra", response = SquadraResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Squadre cercate", response = SquadraResponse.class),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "errore di server")})
    @PutMapping("/addGiocatore/{id}")
    public ResponseEntity<SquadraResponse> aggiungiGiocatore(@PathVariable @ApiParam(value = "id squadra",
            required = true) @Min(0) @Max(10000) ObjectId id, @Valid @RequestBody @ApiParam(value = "giocatoreDTO", required = true) GiocatoreDTO giocatoreDTO) throws SQLException {
        return ResponseEntity.ok(squadraService.aggiungiGiocatore(id, giocatoreDTO));
    }

    @ApiOperation(value = "Aggiorno una tifoseria se no ne creo una", response = SquadraResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tifoseria aggiornata o creata con sucesso", response = SquadraResponse.class),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "errore di server")})
    @PutMapping("/addTifoseria/{idSquadra}")
    public ResponseEntity<SquadraResponse> aggiungiTifoseria(@PathVariable @ApiParam(value = "id squadra", required =
            true) @Min(0) @Max(10000) ObjectId idSquadra, @RequestBody @ApiParam(value = "tifoseria") @Valid TifoseriaDTO tifoseriaDTO) throws SQLException {
        return ResponseEntity.ok(squadraService.aggiungiTifoseria(idSquadra, tifoseriaDTO));
    }

    @ApiOperation(value = "Elimino squadra con relativi giocatori", response = SquadraResponse.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Squadra eliminata con sucesso", response = SquadraResponse.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "errore di server")})
    @DeleteMapping("/{idSquadra}")
    public ResponseEntity<Void> rimuoviSquadra(@PathVariable @Min(0) @Max(10000) ObjectId idSquadra) throws SQLException {
        squadraService.rimuoviSquadra(idSquadra);
        return ResponseEntity.noContent().build();
    }


}
