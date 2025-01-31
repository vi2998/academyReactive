package it.reactive.torneoDemo.exception;

public class TifoseriaDuplicataException extends CustomException {

    public TifoseriaDuplicataException() {
        super("C1", "Tifoseria gia censita");
    }
}
