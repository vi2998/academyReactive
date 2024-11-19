package academy.esercizi.esercizio_29;

public class Tile {
    // CARTA
    private int valoreCarta;
    private boolean isGirata;


    public Tile(int valoreCarta) {
        this.valoreCarta = valoreCarta;
        this.isGirata = false;

    }

    public int getValoreCarta() {
        return valoreCarta;
    }

    public boolean isGirata() {
        return isGirata;
    }

    public void rivelaCarta() {
        isGirata = true;
    }

    public void copriCarta() {
        isGirata = false;
    }


}
