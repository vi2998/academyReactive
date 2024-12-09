package it.reactive.torneoDemo.eccezioni;

public class SquadraNonPresenteException extends RuntimeException {

    public SquadraNonPresenteException() {
        super("Squadra non presente");
    }
}
