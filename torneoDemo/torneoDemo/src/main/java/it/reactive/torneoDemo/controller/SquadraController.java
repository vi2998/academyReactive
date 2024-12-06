package it.reactive.torneoDemo.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.SquadraGiocatoreDTO;
import it.reactive.torneoDemo.exception.GiocatoreDuplicatoException;
import it.reactive.torneoDemo.exception.SquadraNonPresenteException;
import it.reactive.torneoDemo.resource.Squadra;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "squadre", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SquadraController {

    // TODO autowired del squadraService

    @ApiOperation(value = "Aggiungi una nuova squadra", response = Squadra.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Squadra aggiunta", response = Squadra.class),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server")
    })
    @PostMapping("/salvaSquadra")
    public ResponseEntity<Squadra> salvaSquadra(@RequestBody SquadraDTO squadraDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @ApiOperation(value = "Aggiungi una nuova squadra e una lista giocatori", response = Squadra.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Squadra aggiunta con lista giocatori", response = Squadra.class),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server")
    })
    @PostMapping("/salvaSquadraGiocatori")
    public ResponseEntity<Squadra> salvaSquadraGiocatori(@RequestBody SquadraGiocatoreDTO squadraGiocatoreDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @ApiOperation(value = "Ricerca squadra", response = Squadra.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Squadre cercate"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server")
    })

    @GetMapping("/ricercaSquadre/{completo}")
    public ResponseEntity<List<Squadra>> ricercaSquadre(@PathVariable @ApiParam("Parametro che decide se restituisce le squadre piene o vuote") boolean completo) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @ExceptionHandler(GiocatoreDuplicatoException.class)
    public ResponseEntity<Object> handleGiocatoreDuplicatoException(GiocatoreDuplicatoException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = LocalDateTime.now().format(formatter);
        body.put("timestamp", formatDateTime);
        body.put("code", "C3");
        body.put("message", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SquadraNonPresenteException.class)
    public ResponseEntity<Object> handleSquadraNonPresenteException(SquadraNonPresenteException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = LocalDateTime.now().format(formatter);
        body.put("timestamp", formatDateTime);
        body.put("code", "C4");
        body.put("message", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}