package it.reactive.torneoDemo.eccezioni;

public class TorneoNonTrovatoException extends RuntimeException {
    public TorneoNonTrovatoException() {
        super("Torneo non trovato");
    }
}
