package it.reactive.academy.computer;

public interface SistemaOperativo {
    /* firma dei metodi nome() e getLinguaggio */
    String getNome();

    String getLinguaggio();

    default String getLineSeparator(){
        /* restituisce il valore System.lineSeparator(). */
        return System.lineSeparator();
    }
}
