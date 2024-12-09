package it.reactive.torneoDemo.eccezioni;

public class SquadraDuplicataException extends RuntimeException {

    public SquadraDuplicataException() {
        super("Squadra duplicata");
    }
}

