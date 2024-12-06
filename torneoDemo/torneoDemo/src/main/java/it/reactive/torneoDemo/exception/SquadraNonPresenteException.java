package it.reactive.torneoDemo.exception;

public class SquadraNonPresenteException extends Throwable {
    public SquadraNonPresenteException() {
        super("Squadra non presente");
    }
}
