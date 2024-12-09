package it.reactive.torneoDemo.eccezioni;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    public ResponseEntity<Object> handleSquadraDuplicataException(CustomException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Codice errore", "C1: ");
        body.put("message", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(GiocatoreDuplicatoException.class)
    public ResponseEntity<Object> handleGiocatoreDuplicatoException(GiocatoreDuplicatoException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Codice Errore","C4");
        body.put("message", "Giocatore duplicato");
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SquadraNonPresenteException.class)
    public ResponseEntity<Object> handleSquadraNonPresenteException(SquadraNonPresenteException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", "C4");
        body.put("message", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(SquadraDuplicataException.class)
    public ResponseEntity<Object> handleSquadraDuplicata(SquadraNonPresenteException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", "C5");
        body.put("message", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TorneoNonTrovatoException.class)
    public ResponseEntity<Object> handleTorneoNonTrovatoException(TorneoNonTrovatoException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", "C2");
        body.put("message", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

}
