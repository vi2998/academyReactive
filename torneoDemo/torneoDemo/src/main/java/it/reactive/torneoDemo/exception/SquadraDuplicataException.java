package it.reactive.torneoDemo.exception;

public class SquadraDuplicataException extends RuntimeException {
    public SquadraDuplicataException(String message) {
        super("Squadra già censita");
    }
}
