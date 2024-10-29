package academy.esercizi.esercizio_34;

public class Frazione extends OggettoMatematico implements Raddoppiabile, Triplicabile {
    /*La classe Frazione che estende OggettoMatematico ed implementa Raddoppiabile e Triplicabile e rappresenta una frazione.
    Contiene: */

    // - due variabili di istanza private int numeratore e private int denominatore
    private int numeratore;
    private int denominatore;

    // - il metodo pubblico boolean isFrazionePropria() che controlla
    // se la frazione è una frazione propria (cioè se il rapporto numeratore / denominatore ha resto 0).
    public boolean isFrazionePropria(int numeratore, int denominatore) {
        return (numeratore < denominatore) && (denominatore != 0);
    }

    // - un costruttore pubblico con due argomenti: numeratore e denominatore;
    public Frazione(int numeratore, int denominatore) {
        this.numeratore = numeratore;
        this.denominatore = denominatore;
    }

    // - il metodo ereditato getValore() che restituisce il valore double associato alla frazione.
    @Override
    public double getValore() {
        return (double) (numeratore / denominatore);
    }

    // - il metodo Frazione inversa() che non ha parametri
    // che restituisce la frazione inversa della frazione rappresentata da this,
    // ottenuta scambiando numeratore e denominatore.
    Frazione inversa() {
        return new Frazione(denominatore, numeratore);
    }

    // - i getter per numeratore e denominatore
    public double getNumeratore() {
        return numeratore;
    }

    public int getDenominatore() {
        return denominatore;
    }

    // - l’implementazione del metodo raddoppia()
    // che agisce sul valore della variabile d’istanza v (v è ereditata da OggettoMatematico)
    @Override
    public void raddoppia() {
        super.v *= 2;
    }

    // - l’implementazione del metodo triplica() che agisce sul valore della variabile d’istanza v
    @Override
    public void triplica() {
        super.v *= 3;
    }

    // - l’override del metodo isDimezzabile() che restituisce se il metodo è effettivamente dimezzabile
    @Override
    public boolean isDimezzabile() {
        return (numeratore % 2 == 0) && (denominatore % 2 == 0);
    }

    // - il metodo ereditato stampa() che deve essere sovrascritto
    // per restituire una stringa nella forma (numeratore/denominatore).
    public String stampa() {
        return "Frazione: (" +
                "numeratore=" + numeratore +
                "/ denominatore=" + denominatore +
                ')';
    }
}
