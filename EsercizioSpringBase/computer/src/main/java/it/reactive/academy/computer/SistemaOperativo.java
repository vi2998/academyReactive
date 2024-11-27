package it.reactive.academy.computer;

public interface SistemaOperativo {
    /* firma dei metodi nome() e getLinguaggio */
    public String nome();

    public String getLinguaggio();

    public default String getLineSeparator(){
        /* restituisce il valore System.lineSeparator(). */
        return System.lineSeparator();
    }
}
