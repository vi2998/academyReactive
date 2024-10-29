package academy.esercizi.esercizio_34;

public abstract class OggettoMatematico {
    /*La classe astratta OggettoMatematico. Contiene:
    - il metodo double getValore() che restituisce il valore (memorizzato in una variabile d'istanza chiamata v)
    - il metodo String stampa() che restituisce una stringa che descrive il valore.
    */
    double v;

    public double getValore() {
        return v;
    }

    public String stampa() {
        return "OggettoMatematico{" +
                "v=" + v +
                '}';
    }
}