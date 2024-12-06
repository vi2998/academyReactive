package it.reactive.torneoDemo.exception;

public class GiocatoreDuplicatoException extends RuntimeException {

    public GiocatoreDuplicatoException(String message) {
        super("Giocatore già censito");
    }
}