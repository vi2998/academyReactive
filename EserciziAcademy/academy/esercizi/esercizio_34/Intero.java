package academy.esercizi.esercizio_34;

public class Intero extends OggettoMatematico implements Raddoppiabile, Triplicabile {
    /* La classe Intero che estende OggettoMatematico ed implementa Raddoppiabile e Triplicabile.
    Rappresenta un valore intero. Contiene:
    - una variabile di istanza valore: private int v che memorizza il valore intero associato all'oggetto (ereditata da OggettoMatematico)
    - un costruttore pubblico con un argomento valore che assegna il valore alla variabile di istanza v.
    - il metodo pubblico double getValore() che restituisce il valore associato all'oggetto (sovrascritto ed implementato richiamando quello di OggettoMatematico)
    - il metodo String stampa() che restituisce una stringa del valore intero associato all'oggetto  (ereditato da OggettoMatematico)
    - l’implementazione del metodo raddoppia() che agisce sul valore della variabile d’istanza v
    - l’implementazione del metodo triplica() che agisce sul valore della variabile d’istanza v
    - nessuna azione per il metodo isDimezzabile()
*/
    private int v;

    public Intero(int valore) {
        this.v = valore;
    }

    public double getValore() {
        return v;
    }

    public String stampa() {
        return "Intero{" +
                "v=" + v +
                '}';
    }

    @Override
    public void raddoppia() {
        v *= 2;
    }

    @Override
    public void triplica() {
        v *= 3;
    }

    @Override
    public boolean isDimezzabile() {
        return Raddoppiabile.super.isDimezzabile();
    }
}