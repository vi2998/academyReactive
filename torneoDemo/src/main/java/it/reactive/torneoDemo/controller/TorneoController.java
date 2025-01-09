package it.reactive.torneoDemo.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.model.TorneoModel;
import it.reactive.torneoDemo.resource.TorneoResponse;
import it.reactive.torneoDemo.service.TorneoService;
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
@RequestMapping(value = "tornei", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class TorneoController {

    @Autowired
    TorneoService torneoService;


    @ApiOperation(value = "Creo un nuovo torneo", response = TorneoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Torneo creato con sucesso"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server")
    })
    @PostMapping
    public ResponseEntity<TorneoModel> aggiungiTorneo(@RequestBody @Valid TorneoDTO torneoDTO) throws SQLException {
        return ResponseEntity.status(HttpStatus.CREATED).body(torneoService.aggiungiTorneo(torneoDTO));
    }


    @ApiOperation(value = "Censisco una squadra al torneo", response = TorneoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Squadra aggiunta al torneo con sucesso"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server")
    })
    @PutMapping("/addSquadraToTorneo/{idTorneo}/{idSquadra}")
    public ResponseEntity<TorneoModel> censitaSquadraAlTorneo(@PathVariable @Min(0) @Max(10000) Integer idTorneo,
                                                                 @PathVariable @Min(0) @Max(10000) Integer idSquadra) throws SQLException {
        return ResponseEntity.ok(torneoService.associaTorneoASquadra(idTorneo, idSquadra));
    }


    @ApiOperation(value = "Ritorna tutti i tornei con tutte le squadre", response = TorneoResponse.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tornei e squadre recuperato con sucesso"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server")
    })
    @GetMapping()
    public ResponseEntity<List<TorneoResponse>> getTorneoEndSquadre() {
        return ResponseEntity.ok(null);
    }

    @ApiOperation(value = "Elimino il torneo con relative squadre associate se non fanno parte di una altro torneo con relativi giocatori",
            response = TorneoResponse.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tornei, squadre e giocatori eliminati con successo"),
            @ApiResponse(code = 400, message = "Dati inseriti non validi"),
            @ApiResponse(code = 500, message = "Errore del server")
    })
    @DeleteMapping("/{idTorneo}")
    public ResponseEntity<Void> eliminaTorneoConSquadreAndGiocatori(@PathVariable @Min(0) @Max(10000) Integer idTorneo) throws SQLException {
       torneoService.eliminaTorneo(idTorneo);
        return ResponseEntity.noContent().build();
    }

}
