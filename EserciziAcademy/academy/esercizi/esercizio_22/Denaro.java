package academy.esercizi.esercizio_22;

public enum Denaro {
    DOLLAR("dollari", 100),
    QUARTER("quarti di dollaro",25),
    DIME("dieci centesimi",10),
    NICKEL("cinque centesimi",5),
    PENNY("centesimi",1);

    private int denaro;
    private String nome;

    Denaro(String nome, int denaro) {
        this.nome = nome;
        this.denaro = denaro;
    }

    public String getNome() {
        return nome;
    }

    public int getDenaro() {
        return denaro;
    }

}
