package academy.esercizi.esercizio_29_1;

public class Grid {
    private Tile carta;
    private Location coordinate;

    public Grid(Tile carta, Location coordinate) {
        this.carta = carta;
        this.coordinate = coordinate;
    }

    public Tile getCarta() {
        return carta;
    }
}
