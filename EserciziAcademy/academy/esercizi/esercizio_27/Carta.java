package academy.esercizi.esercizio_27;

import java.util.Objects;

public class Carta {
    private int valore;
    private Semi seme;

    public Carta(int valore, Semi seme) {
        this.valore = valore;
        this.seme = seme;
    }

    public int getValore() {
        return valore;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }

    public Semi getSeme() {
        return seme;
    }

    public void setSeme(Semi seme) {
        this.seme = seme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return valore == carta.valore && Objects.equals(seme, carta.seme);
    }

    public boolean equalsValore(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return valore == carta.valore;
    }

    public boolean equalsSeme(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return Objects.equals(seme, carta.seme);
    }

    @Override
    public String toString() {
        return valore + seme.getCodice();
    }
}
